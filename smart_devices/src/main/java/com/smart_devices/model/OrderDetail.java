	package com.smart_devices.model;
	
	import com.smart_devices.dto.OrderDetailDto;

import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	import jakarta.persistence.Table;
	import lombok.AllArgsConstructor;
	import lombok.Builder;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Data
	@Entity
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Table(name = "order_details")
	public class OrderDetail {
	
		
		@Id
		@Column(name = "order_detail_id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int id;
		
		@ManyToOne
		@JoinColumn(name = "order_id")
		Order order;
		
		@ManyToOne
		@JoinColumn(name = "product_detail_id")
		ProductDetail productDetail;
	
		@Column(name = "quantity")
		int quantity;
		
		@Column(name = "price")
		double price;
		
		
	}
	
