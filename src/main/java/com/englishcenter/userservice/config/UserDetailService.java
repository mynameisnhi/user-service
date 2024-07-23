package com.englishcenter.userservice.config;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.englishcenter.userservice.entity.Users;
import com.englishcenter.userservice.repository.UserRepository;

//@Service
@Component
public class UserDetailService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Users user = userRepository.findByUsername(username);
		Optional<Users> user = Optional.ofNullable(userRepository.findByUsername(username));
		
//		if (user != null) {
//			var springUser = User.withUsername(user.getUsername())
//								.password(user.getPassword())
//								.roles(user.getRole())
//								.build();
//			return springUser;
//		}
//		return null;
		
//		if (user != null) {
//            return new UserDetail(user);
//        }
//
//        throw new UsernameNotFoundException("User not found: " + username);
        
//        return user.map(UserDetail::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
        
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Users users = user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(
                users.getUsername(),
                users.getPassword(),
                Collections.emptyList() // Here you can add roles or authorities if needed
        );

	}

}
