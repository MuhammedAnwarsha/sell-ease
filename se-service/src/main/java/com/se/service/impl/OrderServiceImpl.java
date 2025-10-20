package com.se.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.se.payload.dto.SellerDashboardDto;
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

	@Override
	public SellerDashboardDto getSellerDashboard(String fromDateStr, String toDateStr) {
		
		User seller = userService.getCurrentUser();
		LocalDateTime fromDate = null;
	    LocalDateTime toDate = null;

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    if (fromDateStr != null) {
	        fromDate = LocalDate.parse(fromDateStr, formatter).atStartOfDay();
	    }

	    if (toDateStr != null) {
	        toDate = LocalDate.parse(toDateStr, formatter).atTime(23, 59, 59);
	    }

	    // Fetch orders for this seller
	    List<Order> orders;
	    if (fromDate != null && toDate != null) {
	        orders = orderRepository.findByProductSellerIdAndCreatedAtBetween(seller.getId(), fromDate, toDate);
	    } else {
	        orders = orderRepository.findByProductSellerId(seller.getId());
	    }

	    SellerDashboardDto dto = new SellerDashboardDto();
	    dto.setTotalProducts(productRepository.countBySellerId(seller.getId()));
	    dto.setTotalOrders(orders.size());

	    // Orders by status
	    Map<String, Long> statusCount = orders.stream()
	            .collect(Collectors.groupingBy(o -> o.getStatus().name(), Collectors.counting()));
	    dto.setOrdersByStatus(statusCount);

	    // Total earnings
	    double earnings = orders.stream()
	            .mapToDouble(o -> o.getProduct().getPrice() * o.getQuantity())
	            .sum();
	    dto.setTotalEarnings(earnings);

	    // Top products
	    Map<String, Long> topProducts = orders.stream()
	            .collect(Collectors.groupingBy(o -> o.getProduct().getProductName(), Collectors.counting()));
	    dto.setTopProducts(topProducts);

	    return dto;
	}

}
