package com.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se.payload.dto.SellerDashboardDto;
import com.se.payload.response.ApiResponse;
import com.se.service.OrderService;
import com.se.service.ProductService;
import com.se.service.UserService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	
	@Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<SellerDashboardDto>> getSellerDashboard(
            @RequestParam(name = "fromDate",required = false) String fromDate, // yyyy-MM-dd
            @RequestParam(name = "toDate",required = false) String toDate) {

        SellerDashboardDto dashboard = orderService.getSellerDashboard(fromDate, toDate);
        return ResponseEntity.ok(new ApiResponse<>(true, "Dashboard data fetched successfully", dashboard));
    }


}
