package com.app.ecom.Service;

import com.app.ecom.Entity.CartItem;
import com.app.ecom.Entity.Product;
import com.app.ecom.Entity.User;
import com.app.ecom.Repository.CartIteamRepository;
import com.app.ecom.Repository.ProductRepository;
import com.app.ecom.Repository.UserRepository;
import com.app.ecom.dto.CartItemRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartIteamRepository cartIteamRepository;
    private final UserRepository userRepository;

    public Boolean addToCart(String userId, CartItemRequest cartItemRequest) {

        Optional<Product> ProductOtp =
                productRepository.findById(cartItemRequest.getProductId());

        if (ProductOtp.isEmpty()) {
            return false;
        }

        Product product = ProductOtp.get();

        if (product.getStockquantity() < cartItemRequest.getQuantity()) {
            return false;
        }

        Optional<User> userOtp =
                userRepository.findById(Long.parseLong(userId));

        if (userOtp.isEmpty()) {
            return false;
        }

        User user = userOtp.get();

        CartItem existingCartitem =
                cartIteamRepository.findByUserAndProduct(user, product);

        if (existingCartitem != null) {

            existingCartitem.setQuantity(
                    existingCartitem.getQuantity()
                            + cartItemRequest.getQuantity()
            );

            existingCartitem.setPrice(
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            existingCartitem.getQuantity()
                                    )
                            )
            );

            cartIteamRepository.save(existingCartitem);

        } else {

            CartItem newCartitem = new CartItem();

            newCartitem.setUser(user);
            newCartitem.setProduct(product);
            newCartitem.setQuantity(cartItemRequest.getQuantity());

            newCartitem.setPrice(
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            newCartitem.getQuantity()
                                    )
                            )
            );

            cartIteamRepository.save(newCartitem);
        }

        return true;
    }
    @Transactional
    public boolean deleteItemFromCart(String userId, String productId) {

        Optional<Product> ProductOtp =
                productRepository.findById(Long.valueOf(productId));

        if (ProductOtp.isEmpty()) {
            return false;
        }

        Optional<User> userOtp =
                userRepository.findById(Long.parseLong(userId));

        if (userOtp.isEmpty()) {
            return false;
        }

        return userOtp.flatMap(user -> ProductOtp.map(product -> {
            cartIteamRepository.deleteByUserAndProduct(user, product);
            return true;
        })).orElse(false);
    }

    public List<CartItem> getCartItems(String userId) {
        return  userRepository.findById(Long.valueOf(userId))
                .map(cartIteamRepository:: findAllByUser)
                .orElse(List.of());
    }
@Transactional
    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId))
                .ifPresent(cartIteamRepository::deleteAllByUser);
    }
}