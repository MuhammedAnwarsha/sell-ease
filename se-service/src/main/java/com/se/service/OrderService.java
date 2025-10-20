package com.se.service;

import org.springframework.data.domain.Page;

import com.se.domain.OrderStatus;
import com.se.entity.Order;
import com.se.payload.dto.SellerDashboardDto;
import com.se.payload.request.OrderRequestDto;
import com.se.payload.response.OrderResponseDto;

public interface OrderService {
	
	Order createOrder(OrderRequestDto request);
	
    Page<Order> getSellerOrders(Long sellerId, int page, int size);
    
    void updateOrderStatus(Long orderId, OrderStatus status, String jwt);
    
    OrderResponseDto updateOrder(Long orderId, OrderResponseDto  updatedOrder);

    SellerDashboardDto getSellerDashboard(String fromDateStr, String toDateStr);
}
