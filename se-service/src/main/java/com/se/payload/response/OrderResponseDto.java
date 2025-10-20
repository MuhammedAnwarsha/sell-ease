package com.se.payload.response;

import java.time.LocalDateTime;

public class OrderResponseDto {
	
	 private Long id;
	 
	 private String productName;
	 
	 private String buyerName;
	 
	 private String buyerPhone;
	 
	 private String shippingAddress;
	 
	 private int quantity;
	 
	 private double totalAmount;
	 
	 private String status;
	 
	 private LocalDateTime createdAt;

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }

	 public String getProductName() {
		 return productName;
	 }

	 public void setProductName(String productName) {
		 this.productName = productName;
	 }

	 public String getBuyerName() {
		 return buyerName;
	 }

	 public void setBuyerName(String buyerName) {
		 this.buyerName = buyerName;
	 }

	 public String getBuyerPhone() {
		 return buyerPhone;
	 }

	 public void setBuyerPhone(String buyerPhone) {
		 this.buyerPhone = buyerPhone;
	 }

	 public String getShippingAddress() {
		 return shippingAddress;
	 }

	 public void setShippingAddress(String shippingAddress) {
		 this.shippingAddress = shippingAddress;
	 }

	 public int getQuantity() {
		 return quantity;
	 }

	 public void setQuantity(int quantity) {
		 this.quantity = quantity;
	 }

	 public String getStatus() {
		 return status;
	 }

	 public void setStatus(String status) {
		 this.status = status;
	 }

	 public LocalDateTime getCreatedAt() {
		 return createdAt;
	 }

	 public void setCreatedAt(LocalDateTime createdAt) {
		 this.createdAt = createdAt;
	 }

	 public double getTotalAmount() {
		 return totalAmount;
	 }

	 public void setTotalAmount(double totalAmount) {
		 this.totalAmount = totalAmount;
	 }

}
