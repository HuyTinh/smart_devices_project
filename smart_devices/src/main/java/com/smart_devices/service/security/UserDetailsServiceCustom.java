package com.smart_devices.service.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import com.smart_devices.exception.BaseException;
import com.smart_devices.model.User;
import com.smart_devices.repository.UserRepository;


public class UserDetailsServiceCustom implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetailsCustom userDetailsCustom = getUserDetailsCustom(username);
		System.out.println(username);
		if (ObjectUtils.isEmpty(userDetailsCustom)) {
			throw new UsernameNotFoundException("User not found");
		}
		return userDetailsCustom;
	}

	private UserDetailsCustom getUserDetailsCustom(String username) {
		
		User user = userRepository.findByEmail(username);

		if (ObjectUtils.isEmpty(user)) {
			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST), "User not found");
		}

		return new UserDetailsCustom(user.getEmail(), user.getPassword(),
				user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()),
				user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked(),
				user.isCredentialsNonExpired());
	}

}
