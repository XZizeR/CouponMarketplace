package com.coupon.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coupon.beans.Customer;
import com.coupon.repository.CustomersRepository;

@Repository
public class CustomersDBDAO {
	
	@Autowired
	private CustomersRepository repo;
	
//	EXIST - CUSTOMER
	public Customer isCustomerExist(String email, String password) {
		return repo.findCustomerByEmailAndPassword(email, password);
	}
	
//	CREATE CUSTOMER
	public void addCustomer(Customer customer) {
		repo.save(customer);
	}
	
//	UPDATE CUSTOMER
	public void updateCustomer(Customer customer) {
		if(repo.existsById(customer.getCustomerID()))
			repo.save(customer);
	}
	
//	DELETE CUSTOMER
	public void deleteCustomser(long customerId) {
		repo.deleteById(customerId);
	}
	
//	GET-ONE CUSTOMER
	public Customer getOneCustomer(long customerId) {
		Optional<Customer> opt = repo.findById(customerId);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}
	
//	GET-ALL CUSOTMERS
	public List<Customer> getAllCustomers(){
		return repo.findAll();
	}
	
}