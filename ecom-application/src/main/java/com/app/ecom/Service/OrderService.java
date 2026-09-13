package com.app.ecom.Service;

import com.app.ecom.Entity.*;
import com.app.ecom.Repository.OrderRepository;
import com.app.ecom.Repository.UserRepository;
import com.app.ecom.dto.OrderItemDto;
import com.app.ecom.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private  final CartService cartService;
    private final UserRepository userRepository;
    private  final OrderRepository orderRepository;
    public Optional<Object> createOrder(String userId) {
        // validate for cart items
        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        //valid for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        // calculate total price
        BigDecimal totalAmount = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // create order
 Order order = new Order();
 order.setUser(user);
 order.setStatus(OrderStatus.CONFIRMED);
 order.setTotalAmount(totalAmount);
 List<OrderItem> orderItems =cartItems.stream()
         .map(cartItem ->  new OrderItem(
                 null,
                 cartItem.getProduct(),
                 cartItem.getQuantity(),
                 cartItem.getPrice(),
                 order
         )).toList();
    order.setItems(orderItems);
    Order savedOrder= orderRepository.save(order);
    // clear cart
    cartService.clearCart(userId);
    return Optional.of(mapToOrderResponse(savedOrder));
    }

    private Object mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDto(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        )).toList(),
                order.getCreatedAt()

        );
    }

}
