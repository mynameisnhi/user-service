package com.englishcenter.userservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.englishcenter.userservice.entity.RegisterDto;
import com.englishcenter.userservice.entity.Users;
import com.englishcenter.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public Users save(RegisterDto registerDto) {
		Users user = new Users();
		user.setId(registerDto.getId());
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setRole(registerDto.getRole());
		return userRepository.save(user);
	}

	@Override
	public Users findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<Users> getAccountsByRole(String role) {
		List<Users> users = userRepository.findByRole(role);

        return users.stream()
                .map(user -> new Users(
                		user.getId(),
                		user.getUsername(),
                		user.getPassword(),
                        user.getRole()))
                .collect(Collectors.toList());
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
}
