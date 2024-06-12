package com.smart_devices.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smart_devices.dto.OrderDetailManageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart_devices.dto.DailyRevenueDto;
import com.smart_devices.dto.MonthlyRevenueDto;
import com.smart_devices.dto.OrderDetailDto;
import com.smart_devices.enums.OrderStatus;
import com.smart_devices.model.Order;
import com.smart_devices.repository.OrderRepository;
import com.smart_devices.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Override
	public Order findById(int id) {
		// TODO Auto-generated method stub
		return orderRepository.findById(id).get();
	}

	@Override
	public Order save(Order order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order);
	}

	@Override
	public Page<OrderDetailManageDto> findAllOrderDetails(Pageable pageable) {
		return orderRepository.findAllOrderDetails(pageable);
	}
	
	@Override
	public void updateOrderStatus(int orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));
        order.setStatus(status);
        orderRepository.save(order);
    }

	

	@Override
	public Page<OrderDetailManageDto> searchOrders(String keyword, Date fromDate, Date toDate, OrderStatus status,
			Double minTotal, Double maxTotal, Pageable pageable) {
		return orderRepository.searchOrders(keyword, fromDate, toDate, status, minTotal, maxTotal, pageable);
	}

	@Override
	public List<MonthlyRevenueDto> findMonthlyRevenue() {
		return orderRepository.findMonthlyRevenue();
	}
	 @Override
	public List<MonthlyRevenueDto> findMonthlyRevenueWithDailyDetails() {
	        List<MonthlyRevenueDto> monthlyRevenues = orderRepository.findMonthlyRevenue();

	        for (MonthlyRevenueDto monthlyRevenue : monthlyRevenues) {
	            List<DailyRevenueDto> dailyRevenues = orderRepository.findDailyRevenue(monthlyRevenue.getMonth(), monthlyRevenue.getYear());
	            monthlyRevenue.setDailyRevenues(dailyRevenues);
	        }

	        return monthlyRevenues;
	    }


	
	 
	
}
