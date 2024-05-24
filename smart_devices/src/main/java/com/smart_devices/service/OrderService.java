package com.smart_devices.service;


import com.smart_devices.model.Order;


public interface OrderService {
	Order findById(int id);
	
	Order save(Order order);
}
