package com.smart_devices.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gpus")
public class Gpu extends Model {
	
	@Id
	@Column(name = "gpu_id")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	int id;
	
	@Column(name = "capacity")
	String capacity;
	
	@OneToMany(fetch = FetchType.LAZY ,mappedBy = "gpu", cascade = CascadeType.ALL)
	List<ProductDetail> productDetails;
}
