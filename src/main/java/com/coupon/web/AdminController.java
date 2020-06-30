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

// session - the connection
// token - the current connected device

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	Map<String, Session> sessionMap;

	// Checks the session time - works
	// old - version
//	private void isTimeOut(String token, Session session) {
//		long diff = System.currentTimeMillis() - session.getLastAccessed();
//		long limit = 1000 * 60 * 5; 
//		if (diff > limit) {
//			sessionsMap.remove(token);
//			session = null;
//		} 
//	}
	// Checks the session time - works
	private void isTimeOut(String token, Session session) {
		long diff = System.currentTimeMillis() - session.getLastAccessed();
		// q: does the amount of time, adds after constant usage?
		long limit = 1000 * 60 * 60; // milliseconds * seconds * minutes
		if (diff > limit) {
			sessionMap.remove(token);
			session = null;
		} else
			session.setLastAccessed(System.currentTimeMillis()); // updates the last session time

		// Tests:
		System.out.println("AdminController: ");
		System.out
				.println(" getLastAccessed: " + session.getLastAccessed() + ", system: " + System.currentTimeMillis());
		System.out.println("  diff: " + diff + ", limit: " + limit);
		
	}

//	--------------------------------------------------------------------------------------------------
//	Company side:

	// Add a Company - works
	@PostMapping("/addCompany/{token}")
	public ResponseEntity<Object> addCompany(@PathVariable String token, @RequestBody Company company) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
//			session.setLastAccessed(System.currentTimeMillis()); // updates the last session time
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

	// Update a Company - works
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

	// Delete a Company - works
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

	// Get all Company - works
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

	// Get one Company - works
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

//  =================================================================================================
//	Customer side:

	// Add a Customer - works
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

	// Update a Customer - works
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

	// Delete a Customer - works
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

	// Get all Customer - works
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

	// Get one Customer - works
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
