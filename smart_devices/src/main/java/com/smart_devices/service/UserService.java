package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Cart;
import com.smart_devices.model.User;


public interface UserService {
	List<User> findAll();
	
	List<Cart> getCartList();
	
	void setLoginUser(User user);
	
	User getLoginUser();
	
	User findById(int id);
	
	void save(User user);
	
	void delete(User user);
}
