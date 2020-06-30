package com.coupon.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coupon.beans.Company;
import com.coupon.beans.Coupon;
import com.coupon.beans.Customer;
import com.coupon.exception.CompanyExistsException;
import com.coupon.exception.CustomerExistsException;
import com.coupon.exception.EmptyFieldException;
import com.coupon.exception.IdDoesntExistsException;

@Service
public class AdminFacade extends ClientFacade {

	// Login into the system as Administrator
	@Override
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		} else
			return false;
	}

//	--------------------------------------------------------------------------------------------------
//	Company side:

	// Add a Company
	public void addCompany(Company company) throws CompanyExistsException, EmptyFieldException {
		// check - if the fields empty.
		if (company.getName() == null || company.getEmail() == null || company.getPassword() == null) {
			throw new EmptyFieldException();
		}
		// check - if the object already exist.
		List<Company> companies = compDB.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getEmail().equals(company.getEmail()))
				throw new CompanyExistsException();
		}

		// the creation
		compDB.addCompany(company);
		compDB.updateCompany(company);
	}

	// Update a Company
	public void updateCompany(Company company)
			throws CompanyExistsException, EmptyFieldException, IdDoesntExistsException {
		// check - if the fields empty.
		if (company.getName() == null || company.getEmail() == null || company.getPassword() == null) {
			throw new EmptyFieldException();
		}
		// check - if the object exist.
		if (compDB.getOneCompany(company.getCompanyID()) == null) {
			throw new IdDoesntExistsException();
		}
		// check - if the object already exist.
		List<Company> companies = compDB.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getEmail().equals(company.getEmail()))
				throw new CompanyExistsException();
		}

		// the update
		compDB.updateCompany(company);
	}

	// Delete a Company
	public void deleteCompany(int companyID) throws IdDoesntExistsException {
		// check - if the object exist.
		if (compDB.getOneCompany(companyID) == null) {
			throw new IdDoesntExistsException();
		}

		// remove the company coupons from the customers
		List<Customer> customers = custDB.getAllCustomers();
		List<Coupon> trash = new ArrayList<Coupon>();
		for (Customer cust : customers) {
			List<Coupon> custCoupons = cust.getCoupons();
			for (Coupon coup : custCoupons) {
				if (coup.getCompanyID().getCompanyID() == companyID) {
					trash.add(coup);
				}
			}
			for (Coupon coup : trash) {
				custCoupons.remove(coup);
				coupDB.deleteCoupon(coup.getCouponID());
			}
			custDB.updateCustomer(cust);
		}

		// the deletion
		List<Coupon> companyCoupons = compDB.getOneCompany(companyID).getCoupons();
		companyCoupons.clear();
		compDB.deleteCompany(companyID);
	}

	// Get all Companies
	public List<Company> getAllCompanies() {
		return compDB.getAllCompanies();
	}

	// Get one Company
	public Company getOneCompany(int companyID) throws IdDoesntExistsException {
		// check - if the object exist
		if (compDB.getOneCompany(companyID) == null) {
			throw new IdDoesntExistsException();
		}

		// the search
		return compDB.getOneCompany(companyID);
	}

//  =================================================================================================
//	Customer side:

	// Add a Customer
	public void addCustomer(Customer customer) throws CustomerExistsException, EmptyFieldException {
		// check - if the fields empty.
		if (customer.getFirstName() == null || customer.getLastName() == null || customer.getEmail() == null
				|| customer.getPassword() == null) {
			throw new EmptyFieldException();
		}
		// check - if the object already exist.
		List<Customer> customers = custDB.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				throw new CustomerExistsException();
		}

		// the creation
		custDB.addCustomer(customer);
		custDB.updateCustomer(customer);
	}

	// Update a Customer
	public void updateCustomer(Customer customer)
			throws CustomerExistsException, EmptyFieldException, IdDoesntExistsException {
		// check - if the fields empty.
		if (customer.getFirstName() == null || customer.getLastName() == null || customer.getEmail() == null
				|| customer.getPassword() == null) {
			throw new EmptyFieldException();
		}
		// check - if the object exist
		if (custDB.getOneCustomer(customer.getCustomerID()) == null) {
			throw new IdDoesntExistsException();
		}
		// check - if the object already exist.
		List<Customer> customers = custDB.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				throw new CustomerExistsException();
		}

		// the update
		custDB.updateCustomer(customer);
	}

	// Delete a Customer
	public void deleteCustomer(int customerID) throws IdDoesntExistsException {
		Customer cust = custDB.getOneCustomer(customerID);
		// check - if the object exist
		if (cust == null) {
			throw new IdDoesntExistsException();
		}

		// the deletion
		cust.getCoupons().clear();
		custDB.updateCustomer(cust);
		custDB.deleteCustomser(customerID);
	}

	// Get all Customers
	public List<Customer> getAllCustomers() {
		return custDB.getAllCustomers();
	}

	// Get one Customer
	public Customer getOneCustomer(int customerID) throws IdDoesntExistsException {
		// check - if the object exist
		if (custDB.getOneCustomer(customerID) == null) {
			throw new IdDoesntExistsException();
		}

		// the search
		return custDB.getOneCustomer(customerID);
	}

}