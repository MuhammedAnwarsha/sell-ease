package com.se.mapper;

import com.se.entity.Product;
import com.se.payload.dto.ProductDto;

public class ProductMapper {

	public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setPrice(product.getPrice());
        dto.setProductImage(product.getProductImage());
        dto.setQuantity(product.getQuantity());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}
