package com.englishcenter.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.englishcenter.userservice.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	public Users findByUsername(String username);
	public List<Users> findByRole(String role);
    boolean existsByUsername(String username);
}
