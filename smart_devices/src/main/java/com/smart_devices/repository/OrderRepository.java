package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
