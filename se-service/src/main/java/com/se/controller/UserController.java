package com.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.entity.User;
import com.se.mapper.UserMapper;
import com.se.payload.dto.UserDto;
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
	public ResponseEntity<UserDto> getUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {

		User user = userService.getUserById(id);
		return ResponseEntity.ok(UserMapper.toDto(user));
	}

}
