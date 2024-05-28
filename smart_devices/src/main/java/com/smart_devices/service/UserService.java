package com.smart_devices.service;

import java.util.List;

import com.smart_devices.dto.UserDto;
import com.smart_devices.model.User;
import com.smart_devices.response.BaseResponse;

public interface UserService {
	List<User> findAll();
	
	User getCurrentUser();
	
	User findById(int id);
	
	User findByUsername(String username);
	
	BaseResponse registerAccount(UserDto userDTO);
	
	void save(User user);
	
	void delete(User user);
}
