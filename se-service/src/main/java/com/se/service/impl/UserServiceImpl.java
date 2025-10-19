package com.se.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.se.configuration.JwtProvider;
import com.se.entity.User;
import com.se.exception.ResourceNotFoundException;
import com.se.exception.UserException;
import com.se.payload.request.UserUpdateRequest;
import com.se.repository.UserRepository;
import com.se.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public User getUserFromJwtToken(String token) {

		String email = jwtProvider.getEmailFromToken(token);

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("Invalid Token!");
		}
		return user;
	}

	@Override
	public User getCurrentUser() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("User not found");
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("User not found");
		}
		return user;
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User updateCurrentUser(UserUpdateRequest request) {
		
		User user = getCurrentUser();
		
		if(request.getFullname() != null) {
			user.setFullname(request.getFullname());
		}
		
		if (request.getPhone() != null) {
	        user.setPhone(request.getPhone());
	    }

	    if (request.getPassword() != null && !request.getPassword().isBlank()) {
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	    }
	    
	    user.setUpdatedAt(LocalDateTime.now());
	    
		return userRepository.save(user);
	}

}
