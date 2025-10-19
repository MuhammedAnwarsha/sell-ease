package com.se.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.se.configuration.JwtProvider;
import com.se.domain.UserRole;
import com.se.entity.User;
import com.se.exception.UserException;
import com.se.mapper.UserMapper;
import com.se.payload.dto.UserDto;
import com.se.payload.response.AuthResponse;
import com.se.repository.UserRepository;
import com.se.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CustomUserServiceImpl customUserServiceImpl;

	@Override
	public AuthResponse signup(UserDto userDto) throws UserException {

		User user = userRepository.findByEmail(userDto.getEmail());
		if (user != null) {
			throw new UserException("Email is already registered with another user!");
		}

		if (userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
			throw new UserException("Role admin is not allowed!");
		}

		User newUser = new User();
		newUser.setEmail(userDto.getEmail());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		newUser.setRole(userDto.getRole());
		newUser.setPhone(userDto.getPhone());
		newUser.setFullname(userDto.getFullname());
		newUser.setLastLogin(LocalDateTime.now());
		newUser.setCreatedAt(LocalDateTime.now());
		newUser.setUpdatedAt(LocalDateTime.now());
		User savedUser = userRepository.save(newUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(),
				userDto.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("User Registration Successfully Completed");
		authResponse.setUser(UserMapper.toDto(savedUser));

		return authResponse;
	}

	@Override
	public AuthResponse login(UserDto userDto) throws UserException {

		String email = userDto.getEmail();
		String password = userDto.getPassword();

		Authentication authentication = authenticate(email, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		String role = authorities.iterator().next().getAuthority();
		String jwt = jwtProvider.generateToken(authentication);

		User user = userRepository.findByEmail(email);

		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Successfully");
		authResponse.setUser(UserMapper.toDto(user));

		return authResponse;
	}

	private Authentication authenticate(String email, String password) throws UserException {

		try {
			UserDetails userDetails = customUserServiceImpl.loadUserByUsername(email);

			if (!passwordEncoder.matches(password, userDetails.getPassword())) {
				throw new UserException("Invalid Password!");
			}

			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		} catch (UsernameNotFoundException ex) {
			throw new UserException(ex.getMessage());
		}
	}

}
