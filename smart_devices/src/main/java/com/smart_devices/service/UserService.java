package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.User;


public interface UserService {
	List<User> findAll();
	
	User findById(int id);

	void save(User user);
	
	void delete(User user);
}
