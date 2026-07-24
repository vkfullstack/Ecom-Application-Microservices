package com.app.ecom.Contoller;

import com.app.ecom.Service.ProductService;
import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductRequest productRequest) {

        return new ResponseEntity<ProductResponse>(
                productService.createProduct(productRequest),
                HttpStatus.CREATED
        );
    }
}