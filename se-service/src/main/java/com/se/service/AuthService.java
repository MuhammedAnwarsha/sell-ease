package com.se.service;

import com.se.exception.UserException;
import com.se.payload.dto.UserDto;
import com.se.payload.response.AuthResponse;

public interface AuthService {

	AuthResponse signup(UserDto userDto) throws UserException;

	AuthResponse login(UserDto userDto) throws UserException;

}
