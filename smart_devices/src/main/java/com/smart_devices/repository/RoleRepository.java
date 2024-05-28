package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(String name);
}
