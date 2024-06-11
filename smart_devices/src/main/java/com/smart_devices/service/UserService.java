package com.smart_devices.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	Page<User> findAllPage(Pageable pageable);

	Page<User> searchUsers(String keyword, Pageable pageable);

}
