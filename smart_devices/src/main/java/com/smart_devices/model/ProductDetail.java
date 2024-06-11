package com.smart_devices.model;

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
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smart_devices.enums.ProductDetailStatus;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_details")
public class ProductDetail extends Model {
	
	@Id
	@Column(name = "product_detail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;
	
	@Column(name = "series")
	String series;
	
	@Column(name = "version")
	String version;
	
	@Column(name = "title")
	String title;
	
	@ManyToOne
	@JoinColumn(name = "gpu_id")
	Gpu gpu;
	
	@ManyToOne
	@JoinColumn(name = "ram_id")
	Ram ram;
	
	@ManyToOne
	@JoinColumn(name = "storage_id")
	Storage storage;
	
	@ManyToOne
	@JoinColumn(name = "processor_id")
	Processor processor;

	@ManyToOne
	@JoinColumn(name = "operating_system_id")
	OperatingSystem operatingSystem;

	@Column(name = "size")
	String size;
	
	@Column(name = "weight")
	int weight;
	
	@Column(name = "stock")
	int stock;
	
	@Column(name = "price")
	double price;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	ProductDetailStatus status;  
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productDetail", cascade = CascadeType.ALL)
	@JsonIgnore
	List<OrderDetail> orderDetails;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productDetail", cascade = CascadeType.ALL)
	List<ProductImage> productImage;
}

