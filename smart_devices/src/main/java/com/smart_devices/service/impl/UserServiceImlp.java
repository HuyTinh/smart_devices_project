package com.smart_devices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_devices.model.Cart;
import com.smart_devices.model.User;
import com.smart_devices.repository.CartRepository;
import com.smart_devices.repository.UserRepository;
import com.smart_devices.service.UserService;

@Service
public class UserServiceImlp implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;
	
	private User user;
	
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

	public List<Cart> getCartList(){
		return this.user.getCarts();
	}

	@Override
	public void setLoginUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}
	
@Override
	public User getLoginUser() {
		// TODO Auto-generated method stub
		return this.user;
	}
	
}
