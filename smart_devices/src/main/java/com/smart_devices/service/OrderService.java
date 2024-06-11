package com.smart_devices.service;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smart_devices.dto.MonthlyRevenueDto;
import com.smart_devices.dto.OrderDetailDto;
import com.smart_devices.enums.OrderStatus;
import com.smart_devices.model.Order;


public interface OrderService {
	Order findById(int id);
	
	Order save(Order order);

	Page<OrderDetailDto> findAllOrderDetails(Pageable pageable);

	void updateOrderStatus(int orderId, OrderStatus status);

	List<MonthlyRevenueDto> findMonthlyRevenue();

	List<MonthlyRevenueDto> findMonthlyRevenueWithDailyDetails();

	Page<OrderDetailDto> searchOrders(String keyword, Date fromDate, Date toDate, OrderStatus status, Double minTotal, Double maxTotal, Pageable pageable);









	

}
