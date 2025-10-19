package com.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.entity.User;
import com.se.mapper.UserMapper;
import com.se.payload.dto.UserDto;
import com.se.payload.request.UserUpdateRequest;
import com.se.payload.response.ApiResponse;
import com.se.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) {

		User user = userService.getUserFromJwtToken(jwt);
		return ResponseEntity.ok(UserMapper.toDto(user));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@RequestHeader("Authorization") String jwt, @PathVariable("id") Long id) {

		User user = userService.getUserById(id);
		return ResponseEntity.ok(UserMapper.toDto(user));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse<UserDto>> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody UserUpdateRequest request) {

		User updatedUser = userService.updateCurrentUser(request);
		
		UserDto userDto = UserMapper.toDto(updatedUser);
		ApiResponse<UserDto> response = new ApiResponse<>(
                true,
                "User profile updated successfully",
                userDto
        );
		return ResponseEntity.ok(response);
	}

}
