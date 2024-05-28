package com.smart_devices.service.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsCustom implements UserDetails {

	/**
	 * 
	 */
	static final long serialVersionUID = 1L;
	
	String username;

	String password;

	List<GrantedAuthority> authorities;

	boolean isEnabled;

	boolean accountNonExpired;

	boolean accountNonLocked;

	boolean credentialsNonExpired;

}