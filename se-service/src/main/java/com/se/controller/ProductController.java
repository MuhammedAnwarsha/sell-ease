package com.se.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se.entity.Product;
import com.se.mapper.ProductMapper;
import com.se.payload.dto.ProductDto;
import com.se.payload.request.ProductRequest;
import com.se.payload.response.ApiResponse;
import com.se.payload.response.PaginatedResponse;
import com.se.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	@PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product created successfully", ProductMapper.toDto(product)));
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable("id") Long id,
	                                                             @RequestBody ProductRequest request) {
	    Product product = productService.updateProduct(id, request);
	    return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", ProductMapper.toDto(product)));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product deleted successfully", null));
    }
	
	@GetMapping
	public ResponseEntity<ApiResponse<PaginatedResponse<ProductDto>>> getAllProducts(
	        @RequestParam(value = "search", required = false) String search,
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    Page<Product> productPage = productService.getProductsForCurrentUser(search, pageable);

	    List<ProductDto> productDtos = productPage.getContent().stream()
	            .map(ProductMapper::toDto)
	            .collect(Collectors.toList());

	    PaginatedResponse<ProductDto> paginatedResponse = new PaginatedResponse<>(
                productDtos,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
	    
	   return ResponseEntity.ok(new ApiResponse<>(true, "Products fetched successfully", paginatedResponse));
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product fetched successfully", ProductMapper.toDto(product)));
    }
	
	@GetMapping("/{productId}/whatsapp-link")
	public ResponseEntity<ApiResponse<String>> getWhatsappLink(@PathVariable("productId") Long productId) {
	    String link = productService.generateWhatsappLink(productId);
	    return ResponseEntity.ok(new ApiResponse<>(true, "WhatsApp link generated successfully", link));
	}


}
