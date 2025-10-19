package com.se.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
