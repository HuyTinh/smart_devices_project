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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rams")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ram extends Model {

	@Id
	@Column(name = "ram_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "capacity")
	String capacity;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ram", cascade = CascadeType.ALL)
	List<ProductDetail> productDetails; 
	
}
