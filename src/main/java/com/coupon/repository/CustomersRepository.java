package com.coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupon.beans.Customer;

public interface CustomersRepository extends JpaRepository<Customer, Long>{
	
	List<Customer> findCustomerByCustomerID(long customerId);
	
	Customer findCustomerByEmailAndPassword(String email, String password);
}
