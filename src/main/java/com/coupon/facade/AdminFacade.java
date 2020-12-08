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

//	LOGIN - ADMIN
	@Override
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		} else
			return false;
	}


//	ADD COMPANY
	public void addCompany(Company company) throws CompanyExistsException, EmptyFieldException {
//		check - the fields empty
		if (company.getName() == null || company.getEmail() == null || company.getPassword() == null) {
			throw new EmptyFieldException();
		}
//		check - the email already exist
		List<Company> companies = compDB.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getEmail().equals(company.getEmail()))
				throw new CompanyExistsException();
		}

		compDB.addCompany(company);
		compDB.updateCompany(company);
	}

//	UPDATE COMPANY
	public void updateCompany(Company company)
			throws CompanyExistsException, EmptyFieldException, IdDoesntExistsException {
//		check - the fields empty
		if (company.getName() == null || company.getEmail() == null || company.getPassword() == null) {
			throw new EmptyFieldException();
		}
//		check - the object exist
		if (compDB.getOneCompany(company.getCompanyID()) == null) {
			throw new IdDoesntExistsException();
		}
//		check - the email exist
		List<Company> companies = compDB.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getEmail().equals(company.getEmail()))
				throw new CompanyExistsException();
		}

		compDB.updateCompany(company);
	}

//	DELETE COMPANY
	public void deleteCompany(int companyID) throws IdDoesntExistsException {
//		check - the object exist
		if (compDB.getOneCompany(companyID) == null) {
			throw new IdDoesntExistsException();
		}
		
//		remove the company coupons from the customers
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

//		delete the company
		List<Coupon> companyCoupons = compDB.getOneCompany(companyID).getCoupons();
		companyCoupons.clear();
		compDB.deleteCompany(companyID);
	}

//	GET-ALL COMPANIES
	public List<Company> getAllCompanies() {
		return compDB.getAllCompanies();
	}

//	GET-ONE COMPANY
	public Company getOneCompany(int companyID) throws IdDoesntExistsException {
	//	check - the object exist
		if (compDB.getOneCompany(companyID) == null) {
			throw new IdDoesntExistsException();
		}

		return compDB.getOneCompany(companyID);
	}

//	ADD CUSTOMER
	public void addCustomer(Customer customer) throws CustomerExistsException, EmptyFieldException {
//		check - the fields empty
		if (customer.getFirstName() == null || customer.getLastName() == null || customer.getEmail() == null
				|| customer.getPassword() == null) {
			throw new EmptyFieldException();
		}
//	 check - the email exist
		List<Customer> customers = custDB.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				throw new CustomerExistsException();
		}

		custDB.addCustomer(customer);
		custDB.updateCustomer(customer);
	}

//	UPDATE CUSTOMER
	public void updateCustomer(Customer customer)
			throws CustomerExistsException, EmptyFieldException, IdDoesntExistsException {
//		check - the fields empty
		if (customer.getFirstName() == null || customer.getLastName() == null || customer.getEmail() == null
				|| customer.getPassword() == null) {
			throw new EmptyFieldException();
		}
//		check - the object exist
		if (custDB.getOneCustomer(customer.getCustomerID()) == null) {
			throw new IdDoesntExistsException();
		}
//		check - the email already exist
		List<Customer> customers = custDB.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				throw new CustomerExistsException();
		}

		custDB.updateCustomer(customer);
	}

//	DELETE CUSTOMER
	public void deleteCustomer(int customerID) throws IdDoesntExistsException {
		Customer cust = custDB.getOneCustomer(customerID);
//		check - the object exist
		if (cust == null) {
			throw new IdDoesntExistsException();
		}

		cust.getCoupons().clear();
		custDB.updateCustomer(cust);
		custDB.deleteCustomser(customerID);
	}

//	GET-ALL CUSTOMERS
	public List<Customer> getAllCustomers() {
		return custDB.getAllCustomers();
	}

//	GET-ONE CUSTOMER
	public Customer getOneCustomer(int customerID) throws IdDoesntExistsException {
//		check - the object exist
		if (custDB.getOneCustomer(customerID) == null) {
			throw new IdDoesntExistsException();
		}

		return custDB.getOneCustomer(customerID);
	}

}