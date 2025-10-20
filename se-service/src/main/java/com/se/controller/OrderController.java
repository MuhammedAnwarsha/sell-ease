package com.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se.domain.OrderStatus;
import com.se.entity.Order;
import com.se.mapper.OrderMapper;
import com.se.payload.request.OrderRequestDto;
import com.se.payload.response.ApiResponse;
import com.se.payload.response.OrderResponseDto;
import com.se.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
    private OrderService orderService;
	
	@PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order created successfully", OrderMapper.toDto(order)));
    }
	
	@GetMapping
    public ResponseEntity<ApiResponse<Page<OrderResponseDto>>> getSellerOrders(
    	       @RequestParam(name = "page", defaultValue = "0") int page,
    	       @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<OrderResponseDto> orders = orderService.getSellerOrders(null, page, size)
                .map(OrderMapper::toDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orders fetched successfully", orders));
    }

	@PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateStatus(
            @PathVariable("id") Long id,
            @RequestParam(name = "status") OrderStatus status,
            @RequestHeader("Authorization") String jwt) {

        orderService.updateOrderStatus(id, status, jwt);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order status updated orderId", null));
    }
	
	@PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody OrderResponseDto updatedOrder) {

        OrderResponseDto result = orderService.updateOrder(orderId, updatedOrder);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order updated successfully", result));
    }

}
