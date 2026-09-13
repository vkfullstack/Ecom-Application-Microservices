package com.app.ecom.Contoller;

import com.app.ecom.Service.OrderService;
import com.app.ecom.dto.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private  final OrderService orderService;
    @PostMapping
    public ResponseEntity<Optional<Object>> createOrder(@RequestHeader("X-User-Id") String userId) {
        Optional<Object> orderResponse = orderService.createOrder(userId);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }
}
