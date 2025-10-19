package com.se.service;

import java.util.List;

import com.se.entity.User;
import com.se.payload.request.UserUpdateRequest;

public interface UserService {

	User getUserFromJwtToken(String token);

	User getCurrentUser();

	User getUserByEmail(String email);

	User getUserById(Long id);

	List<User> getAllUsers();
	
	User updateCurrentUser(UserUpdateRequest request);
}
