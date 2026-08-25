package com.app.ecom.Contoller;

import com.app.ecom.Service.CardService;
import com.app.ecom.dto.CartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private  final CardService cardService;
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestHeader("X-USER-ID") String User_id, @RequestBody CartItemRequest cartItemRequest) {
       if(!cardService.addToCart(User_id, cartItemRequest)) {;
           return ResponseEntity.badRequest().body("Product not found or insufficient stock");
       }
        // Logic to add item to cart
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-USER-ID") String User_id, @PathVariable String productId) {
        boolean deleted=cardService.deleteItemFromCart(User_id, productId);
        return  deleted ?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }
}
