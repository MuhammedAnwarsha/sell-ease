package com.se.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.se.entity.Product;
import com.se.payload.request.ProductRequest;

public interface ProductService {
	
	Product createProduct(ProductRequest request);

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    Page<Product> getProductsForCurrentUser(String search, Pageable pageable);

}
