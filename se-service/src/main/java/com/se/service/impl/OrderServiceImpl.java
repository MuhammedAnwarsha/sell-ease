package com.se.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.se.domain.OrderStatus;
import com.se.entity.Order;
import com.se.entity.Product;
import com.se.entity.User;
import com.se.exception.ResourceNotFoundException;
import com.se.exception.UserException;
import com.se.mapper.OrderMapper;
import com.se.payload.request.OrderRequestDto;
import com.se.payload.response.OrderResponseDto;
import com.se.repository.OrderRepository;
import com.se.repository.ProductRepository;
import com.se.service.OrderService;
import com.se.service.UserService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

	@Override
	public Order createOrder(OrderRequestDto request) {
		
		Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));
		
		if (product.getQuantity() < request.getQuantity()) {
	        throw new ResourceNotFoundException("Insufficient stock. Only " + product.getQuantity() + " items available.");
	    }

	    double totalAmount = product.getPrice() * request.getQuantity();
		
		 Order order = new Order();
	     order.setProduct(product);
	     order.setBuyerName(request.getBuyerName());
	     order.setBuyerPhone(request.getBuyerPhone());
	     order.setShippingAddress(request.getShippingAddress());
	     order.setQuantity(request.getQuantity());
	     order.setTotalAmount(totalAmount);
	     order.setStatus(OrderStatus.PENDING);
	     order.setCreatedAt(LocalDateTime.now());
	     
	     product.setQuantity(product.getQuantity() - request.getQuantity());
	     productRepository.save(product);
	   
	     return orderRepository.save(order);
	}

	@Override
	public Page<Order> getSellerOrders(Long sellerId, int page, int size) {
		return orderRepository.findAll(PageRequest.of(page, size))
                .map(order -> order); 
	}

	@Override
	public void updateOrderStatus(Long orderId, OrderStatus status, String jwt) {
		
		User seller = userService.getUserFromJwtToken(jwt);
		
		Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
		
		if (!order.getProduct().getSeller().getId().equals(seller.getId())) {
            throw new UserException("You are not authorized to update this order");
        }
        order.setStatus(status);
        orderRepository.save(order);
	}
	
	public OrderResponseDto updateOrder(Long orderId, OrderResponseDto updatedOrder) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setShippingAddress(updatedOrder.getShippingAddress());
        order.setQuantity(updatedOrder.getQuantity());

        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

}
