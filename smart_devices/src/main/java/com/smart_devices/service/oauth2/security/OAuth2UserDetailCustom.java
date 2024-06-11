package com.smart_devices.service.oauth2.security;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2UserDetailCustom implements OAuth2User, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int id;

	String username;

	String password;

	List<GrantedAuthority> authorities;

	Map<String, Object> attributes;
	
	public OAuth2UserDetailCustom(int id, String username, String password,	List<GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	boolean isEnabled;

	boolean accountNonExpired;

	boolean accountNonLocked;

	boolean credentialsNonExpired;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}

	@Override
	public <A> A getAttribute(String name) {
		// TODO Auto-generated method stub
		return OAuth2User.super.getAttribute(name);
	}

}
