package com.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.exception.UserException;
import com.se.payload.dto.UserDto;
import com.se.payload.response.AuthResponse;
import com.se.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(@RequestBody UserDto userDto) throws UserException {
		return ResponseEntity.ok(authService.signup(userDto));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody UserDto userDto) throws UserException {
		return ResponseEntity.ok(authService.login(userDto));
	}

}
