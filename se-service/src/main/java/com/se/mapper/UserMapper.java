package com.se.mapper;

import com.se.entity.User;
import com.se.payload.dto.UserDto;

public class UserMapper {

	public static UserDto toDto(User savedUser) {

		UserDto userDto = new UserDto();
		userDto.setId(savedUser.getId());
		userDto.setEmail(savedUser.getEmail());
		userDto.setCreatedAt(savedUser.getCreatedAt());
		userDto.setLastLogin(savedUser.getLastLogin());
		userDto.setUpdatedAt(savedUser.getUpdatedAt());
		userDto.setPhone(savedUser.getPhone());
		userDto.setFullname(savedUser.getFullname());

		return userDto;
	}

}
