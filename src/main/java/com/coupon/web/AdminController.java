package com.coupon.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.beans.Company;
import com.coupon.beans.Customer;
import com.coupon.exception.CompanyDoestExistsException;
import com.coupon.exception.CompanyExistsException;
import com.coupon.exception.CustomerExistsException;
import com.coupon.exception.EmptyFieldException;
import com.coupon.exception.IdDoesntExistsException;
import com.coupon.facade.AdminFacade;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	Map<String, Session> sessionMap;

//	SESSION
	private void isTimeOut(String token, Session session) {
		long diff = System.currentTimeMillis() - session.getLastAccessed();
		long limit = 1000 * 60 * 60; // 60 minutes
		if (diff > limit) {
			sessionMap.remove(token);
			session = null;
		} else
			session.setLastAccessed(System.currentTimeMillis());	
	}

//	ADD COMPANY
	@PostMapping("/addCompany/{token}")
	public ResponseEntity<Object> addCompany(@PathVariable String token, @RequestBody Company company) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.addCompany(company);
				return ResponseEntity.ok(company);
			} catch (CompanyExistsException | EmptyFieldException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	UPDATE COMPANY
	@PutMapping("/updateCompany/{token}")
	public ResponseEntity<Object> updateCompany(@PathVariable String token, @RequestBody Company company) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.updateCompany(company);
				return ResponseEntity.ok(company);
			} catch (CompanyExistsException | EmptyFieldException | IdDoesntExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	DELETE COUPON
	@DeleteMapping("/deleteCompany/{token}/{companyID}")
	public ResponseEntity<Object> deleteCompany(@PathVariable String token, @PathVariable int companyID)
			throws CompanyDoestExistsException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.deleteCompany(companyID);
				return ResponseEntity.ok(companyID);
			} catch (IdDoesntExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND-ALL COUPONS
	@GetMapping("/getAllCompany/{token}")
	public ResponseEntity<Object> getAllCompany(@PathVariable String token) throws CompanyExistsException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			return ResponseEntity.ok(facade.getAllCompanies());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND-ONE COUPON
	@GetMapping("/getOneCompany/{token}/{companyID}")
	public ResponseEntity<Object> getOneCompany(@PathVariable String token, @PathVariable int companyID)
			throws CompanyExistsException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getOneCompany(companyID));
			} catch (IdDoesntExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	ADD CUSTOMER
	@PostMapping("/addCustomer/{token}")
	public ResponseEntity<Object> addNewCustomer(@PathVariable String token, @RequestBody Customer customer) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.addCustomer(customer);
				return ResponseEntity.ok(customer);
			} catch (CustomerExistsException | EmptyFieldException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	UPDATE CUSTOMER
	@PutMapping("/updateCustomer/{token}")
	public ResponseEntity<Object> updateCustomer(@PathVariable String token, @RequestBody Customer customer) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.updateCustomer(customer);
				return ResponseEntity.ok(customer);
			} catch (CustomerExistsException | EmptyFieldException | IdDoesntExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	DELETE CUSTOMER
	@DeleteMapping("/deleteCustomer/{token}/{customerID}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable String token, @PathVariable int customerID)
			throws CustomerExistsException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.deleteCustomer(customerID);
				return ResponseEntity.ok(customerID);
			} catch (IdDoesntExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND-ALL CUSTOMERS
	@GetMapping("/getAllCustomers/{token}")
	public ResponseEntity<Object> getAllCustomer(@PathVariable String token) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			return ResponseEntity.ok(facade.getAllCustomers());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND-ONE CUSTOMER
	@GetMapping("/getOneCustomer/{token}/{customerID}")
	public ResponseEntity<Object> getOneCustomer(@PathVariable String token, @PathVariable int customerID) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getOneCustomer(customerID));
			} catch (IdDoesntExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
}
