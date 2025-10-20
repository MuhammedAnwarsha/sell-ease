package com.se.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByProductSellerId(Long sellerId);
	
	List<Order> findByProductSellerIdAndCreatedAtBetween(Long sellerId, LocalDateTime from, LocalDateTime to);

}
