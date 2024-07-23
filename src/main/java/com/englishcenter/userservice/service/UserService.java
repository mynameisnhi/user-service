package com.englishcenter.userservice.service;

import java.util.List;

import com.englishcenter.userservice.entity.RegisterDto;
import com.englishcenter.userservice.entity.Users;

public interface UserService {
	 Users save(RegisterDto registerDto);
	 Users findByUsername(String username);
	 List<Users> getAccountsByRole(String role);
	 boolean existsByUsername(String username);
}
