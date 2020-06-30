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
	
	// Customer exists
	public Customer isCustomerExist(String email, String password) {
		return repo.findCustomerByEmailAndPassword(email, password);
	}
	
	// Add a Customer
	public void addCustomer(Customer customer) {
		repo.save(customer);
	}
	
	// Update a Customer
	public void updateCustomer(Customer customer) {
		if(repo.existsById(customer.getCustomerID()))
			repo.save(customer);
	}
	
	// Delete a Customer
	public void deleteCustomser(long customerId) {
		repo.deleteById(customerId);
	}
	
	// Show a Customer
	public Customer getOneCustomer(long customerId) {
		Optional<Customer> opt = repo.findById(customerId);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}
	
	// Show all Customers
	public List<Customer> getAllCustomers(){
		return repo.findAll();
	}
	
}