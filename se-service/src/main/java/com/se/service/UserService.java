package com.se.service;

import java.util.List;

import com.se.entity.User;

public interface UserService {

	User getUserFromJwtToken(String token);

	User getCurrentUser();

	User getUserByEmail(String email);

	User getUserById(Long id);

	List<User> getAllUsers();
}
