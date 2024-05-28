package com.smart_devices.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.smart_devices.enums.Gender;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(name = "first_name")
	String firstName;
	
	@Column(name = "last_name")
	String lastName;
	
	@Column(name = "username")
	String username;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "phone")
	String phone;
	
	@Column(name = "address")
	String address;
	
	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	Date birthDay;
	
	@Column(name = "account_non_expired")
	boolean accountNonExpired;

	@Column(name = "is_enabled")
	boolean isEnabled;

	@Column(name = "account_non_locked")
	boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	boolean credentialsNonExpired;
	
	@Column(name = "provider_id")
	String providerId;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "user_role", joinColumns = 
			@JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	Set<Role> roles;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	Gender gender;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Cart> carts; 
	
}
