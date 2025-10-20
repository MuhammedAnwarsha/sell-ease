package com.se.payload.dto;

import java.util.Map;

public class SellerDashboardDto {

	private long totalProducts;
	
    private long totalOrders;
    
    private Map<String, Long> ordersByStatus;
    
    private double totalEarnings;
    
    private Map<String, Long> topProducts;

	public long getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}

	public long getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(long totalOrders) {
		this.totalOrders = totalOrders;
	}

	public Map<String, Long> getOrdersByStatus() {
		return ordersByStatus;
	}

	public void setOrdersByStatus(Map<String, Long> ordersByStatus) {
		this.ordersByStatus = ordersByStatus;
	}

	public double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public Map<String, Long> getTopProducts() {
		return topProducts;
	}

	public void setTopProducts(Map<String, Long> topProducts) {
		this.topProducts = topProducts;
	}
    
}
