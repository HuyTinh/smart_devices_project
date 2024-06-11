package com.smart_devices.service.impl;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.smart_devices.dto.UserDto;
import com.smart_devices.dto.UserSignUpDto;
import com.smart_devices.enums.Provider;
import com.smart_devices.exception.BaseException;
import com.smart_devices.model.Role;
import com.smart_devices.model.User;
import com.smart_devices.repository.CartRepository;
import com.smart_devices.repository.RoleRepository;
import com.smart_devices.repository.UserRepository;
import com.smart_devices.response.BaseResponse;
import com.smart_devices.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImlp implements UserService {
	
	final UserRepository userRepository;
	
	final CartRepository cartRepository;
	
	final RoleRepository roleRepository;
	
	final BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userRepository.delete(user);
	}

	public List<Cart> getCartList() {
		return this.user.getCarts();

	}
	

	@Override
	public User getCurrentUser() {
		Principal principal =(SecurityContextHolder.getContext().getAuthentication());
		if(principal != null) {
			String userName = principal.getName();
			
			System.out.println(userRepository.findByEmail(userName).getId());
			
			return userRepository.findByEmail(userName);
		}
		return null;
	}

	@Override
	public BaseResponse registerAccount(UserSignUpDto userSignUpDto) {
		// TODO Auto-generated method stub
		BaseResponse response = new BaseResponse();

		// validate data from client
		validateAccount(userSignUpDto);

		User user = insertUser(userSignUpDto);

		try {
			userRepository.save(user);
			response.setCode(String.valueOf(HttpStatus.CREATED.value()));
			response.setMessage("Register account successfully!!!");
		} catch (Exception e) {
			response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
			response.setMessage("Service Unavailable");
		}
		
		return response;
	}

	@Override
	public Page<User> findAllPage(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> searchUsers(String keyword, Pageable pageable) {
		return userRepository.searchUsers(keyword, pageable);
	}

	private User insertUser(UserSignUpDto userSignUpDto) {
		User user = new User();
		user.setEmail(userSignUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));

		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName("USER"));
		user.setRoles(roles);

		user.setFirstName(userSignUpDto.getFirstName());
		user.setLastName(userSignUpDto.getLastName());
		user.setEnabled(true);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);

		user.setProviderId(Provider.local.name());
		
		return user;
	}

	private void validateAccount(UserSignUpDto userSignUpDto) {
		if (ObjectUtils.isEmpty(userSignUpDto)) {
			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
		}

//		try {
//			if (!ObjectUtils.isEmpty(userSignUpDto.checkProperties())) {
//				throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
//			}
//		} catch (IllegalAccessException e) {
//			throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
//		}

//		List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
//
//		if (!roles.contains(userSignUpDto.getRole())) {
//			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
//		}
//
//		User user = userRepository.findByEmail(userSignUpDto.getEmail());
//
//		if (!ObjectUtils.isEmpty(user)) {
//			throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "User had existed!!!");
//		}

	}

}
