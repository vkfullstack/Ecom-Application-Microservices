package com.app.ecom.Service;

import com.app.ecom.Entity.Product;
import com.app.ecom.Repository.ProductRepository;
import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private  final ProductRepository productRepository;

    public  ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        UpdateProductFromRequest(product,productRequest);
        Product savedproduct=productRepository.save(product);
        return  mapToProductResponse(savedproduct);
    }

    private ProductResponse mapToProductResponse(Product savedproduct) {
        ProductResponse response=new ProductResponse();
        response.setId(savedproduct.getId());
        response.setName(savedproduct.getName());
        response.setDescription(savedproduct.getDescription());
        response.setImagerurl(savedproduct.getImagerurl());
        response.setCategory(savedproduct.getCategory());
        response.setPrice(savedproduct.getPrice());
        response.setStockquantity(savedproduct.getStockquantity());
        return response;

    }

    private void UpdateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImagerurl(productRequest.getImagerurl());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setStockquantity(productRequest.getStockquantity());
        }
    }

