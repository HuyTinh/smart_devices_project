package com.smart_devices.service;

import java.util.List;

import com.smart_devices.dto.UserSignUpDto;
import com.smart_devices.model.User;
import com.smart_devices.response.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	List<User> findAll();
	
	User getCurrentUser();
	
	User findById(int id);
	
	User findByEmail(String email);
	
	BaseResponse registerAccount(UserSignUpDto userSignUpDto);
	
	void save(User user);
	
	void delete(User user);

	Page<User> findAllPage(Pageable pageable);

	Page<User> searchUsers(String keyword, Pageable pageable);

}
