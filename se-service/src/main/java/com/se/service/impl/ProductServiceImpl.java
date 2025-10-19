package com.se.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.se.entity.Product;
import com.se.entity.User;
import com.se.exception.ResourceNotFoundException;
import com.se.payload.request.ProductRequest;
import com.se.repository.ProductRepository;
import com.se.service.ProductService;
import com.se.service.UserService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

	@Override
	public Product createProduct(ProductRequest request) {
		
		User seller = userService.getCurrentUser();
		
		Product product = new Product();
		product.setProductName(request.getProductName());
		product.setProductDescription(request.getProductDescription());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setProductImage(request.getProductImage());
		product.setSeller(seller);
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());
		
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long id, ProductRequest request) {
		
	     Product product = getProductById(id);
		
		 if (request.getProductName() != null) {
			 product.setProductName(request.getProductName());
		 }
	     if (request.getProductDescription() != null) {
	    	     product.setProductDescription(request.getProductDescription());
	     }
	     if (request.getPrice() != null) {
	    	     product.setPrice(request.getPrice());
	     }
	     if (request.getProductImage() != null) {
	    	     product.setProductImage(request.getProductImage());
	     }
	     if (request.getQuantity() != null) {
	    	     product.setQuantity(request.getQuantity());
	     }

	     product.setUpdatedAt(LocalDateTime.now());
	     
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Long id) {
		
		Product product = getProductById(id);
		productRepository.delete(product);
	}

	@Override
	public Product getProductById(Long id) {
		Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getSeller().getId().equals(userService.getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }
        
        return product;
	}

	@Override
	public Page<Product> getProductsForCurrentUser(String search, Pageable pageable) {
		
		User seller = userService.getCurrentUser();
		if(search == null || search.isBlank()) {
			return productRepository.findBySeller(seller, pageable);
		}
		return productRepository.findBySellerAndProductNameContainingIgnoreCase(seller, search, pageable);
	}

	@Override
	public String generateWhatsappLink(Long productId) {
	    
		Product product = getProductById(productId);
		
		String phone = product.getSeller().getPhone();
		
		String text = String.format(
	            "Check out this product: %s%nDescription: %s%nPrice: %.2f%nImage: %s",
	            product.getProductName(),
	            product.getProductDescription(),
	            product.getPrice(),
	            product.getProductImage() != null ? product.getProductImage() : ""
	    );
		
		String encodedText = java.net.URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8);
		return "https://wa.me/" + phone + "?text=" + encodedText;
	}
	

}
