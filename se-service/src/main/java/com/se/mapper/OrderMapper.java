package com.se.mapper;

import com.se.entity.Order;
import com.se.payload.response.OrderResponseDto;

public class OrderMapper {
	
	public static OrderResponseDto toDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setProductName(order.getProduct().getProductName());
        dto.setBuyerName(order.getBuyerName());
        dto.setBuyerPhone(order.getBuyerPhone());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setQuantity(order.getQuantity());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

}
