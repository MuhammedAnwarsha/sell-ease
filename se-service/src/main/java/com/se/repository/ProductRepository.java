package com.se.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.se.entity.Product;
import com.se.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Page<Product> findBySeller(User seller, Pageable pageable);
	
	Page<Product> findBySellerAndProductNameContainingIgnoreCase(User seller, String productName, Pageable pageable);
	
	long countBySellerId(Long sellerId);

}
