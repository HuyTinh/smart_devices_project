package com.smart_devices.dto;

import java.sql.Date;

import com.smart_devices.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailManageDto {
	 	private int orderId;
		private String userName;
	    private String productTitle;
	    private int quantity;
	    private double price;
	    private Date orderDate;
	    private String shippingAddress;
	    private OrderStatus orderStatus;
	    private double total;
	    
	    public OrderDetailManageDto(Integer orderId, String userName, String productTitle, Integer quantity, Double price, Date orderDate, String shippingAddress, OrderStatus orderStatus) {
	        this.orderId = orderId;
	        this.userName = userName;
	        this.productTitle = productTitle;
	        this.quantity = quantity;
	        this.price = price;
	        this.orderDate = orderDate;
	        this.shippingAddress = shippingAddress;
	        this.orderStatus = orderStatus;
	        this.total = quantity * price; 
	    }
}
