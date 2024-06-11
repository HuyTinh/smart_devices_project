package com.smart_devices.model;

import java.util.Date;
import java.util.List;


import com.smart_devices.enums.OrderStatus;
import com.smart_devices.enums.PaymentMethod;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Model {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@Column(name = "shipping_address")
	String shippingAddress;
	
	@Column(name = "total_price")
	double totalPrice;
	
	@Column(name = "order_status")
	@Enumerated(EnumType.STRING)
	@Builder.Default
	OrderStatus status = OrderStatus.PROCESSING;

	@Column(name = "order_date")
	@Temporal(TemporalType.DATE)
	Date orderDate;
	@Column(name = "payment_method")
	@Enumerated(EnumType.STRING)
	PaymentMethod paymentMethod;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderDetail> orderDetails;
	
	@Column(name= "order_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Builder.Default
	Date orderDate = new Date();
}
