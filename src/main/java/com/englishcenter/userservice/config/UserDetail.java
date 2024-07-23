package com.englishcenter.userservice.config;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.englishcenter.userservice.entity.Users;

public class UserDetail implements UserDetails {

//	private Users users;
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetail(Users users) {
		this.username = users.getUsername();
		this.password = users.getPassword();
		this.authorities = AuthorityUtils.createAuthorityList(users.getRole());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		String role = users.getRole();
//
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
