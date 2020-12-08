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

import com.coupon.beans.Category;
import com.coupon.beans.Coupon;
import com.coupon.exception.CompanyExistsException;
import com.coupon.exception.CouponExistsException;
import com.coupon.exception.DateException;
import com.coupon.exception.EmptyFieldException;
import com.coupon.exception.EndOfSessionException;
import com.coupon.exception.IdDoesntExistsException;
import com.coupon.exception.IncorrectInputException;
import com.coupon.facade.CompanyFacade;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	@Autowired
	Map<String, Session> sessionMap;

//	SESSION
	private void isTimeOut(String token, Session session) throws EndOfSessionException {
		long diff = System.currentTimeMillis() - session.getLastAccessed();
		long limit = 1000 * 60 * 60; // 60 minutes
		if (diff > limit) {
			sessionMap.remove(token);
			session = null;
			throw new EndOfSessionException();
		} else
			session.setLastAccessed(System.currentTimeMillis());

//		Tests:
//		System.out.println("CompanyController: ");
//		System.out
//				.println(" getLastAccessed: " + session.getLastAccessed() + ", system: " + System.currentTimeMillis());
//		System.out.println("  diff: " + diff + ", limit: " + limit);
	}

//	ADD COUPON
	@PostMapping("/addCoupon/{token}")
	public ResponseEntity<Object> addNewCoupon(@PathVariable String token, @RequestBody Coupon coupon)
			throws EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			try {
				CompanyFacade facade = (CompanyFacade) session.getFacade();
				coupon.setCompanyID(facade.getCompanyDetails());
				facade.addCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (CouponExistsException | EmptyFieldException | DateException | IncorrectInputException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	UPDATE COUPON
	@PutMapping("/updateCoupon/{token}")
	public ResponseEntity<Object> updateCoupon(@PathVariable String token, @RequestBody Coupon coupon)
			throws EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CompanyFacade facade = (CompanyFacade) session.getFacade();
			try {
				coupon.setCompanyID(facade.getCompanyDetails());
				facade.updateCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (CouponExistsException | EmptyFieldException | IdDoesntExistsException | DateException
					| IncorrectInputException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	DELETE COUPON
	@DeleteMapping("/deleteCoupon/{token}/{couponID}")
	public ResponseEntity<Object> deleteCoupon(@PathVariable String token, @PathVariable int couponID)
			throws CouponExistsException, EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CompanyFacade facade = (CompanyFacade) session.getFacade();
			try {
				facade.deleteCoupon(couponID);
				return ResponseEntity.ok(couponID);
			} catch (IdDoesntExistsException | IncorrectInputException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND COUPONS
	@GetMapping("/getCompanyCoupons/{token}")
	public ResponseEntity<Object> getCoupons(@PathVariable String token)
			throws CouponExistsException, EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CompanyFacade facade = (CompanyFacade) session.getFacade();
			return ResponseEntity.ok(facade.getCompanyCoupons());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND COUPONS - CATEGORY
	@GetMapping("/getCompanyCouponsCategory/{token}/{category}")
	public ResponseEntity<Object> getCoupons(@PathVariable String token, @PathVariable Category category)
			throws CouponExistsException, EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CompanyFacade facade = (CompanyFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getCompanyCoupons(category));
			} catch (EmptyFieldException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND COUPONS - PRICE LIMIT
	@GetMapping("/getCompanyCouponsMaxprice/{token}/{maxPrice}")
	public ResponseEntity<Object> getCoupons(@PathVariable String token, @PathVariable double maxPrice)
			throws EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CompanyFacade facade = (CompanyFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getCompanyCoupons(maxPrice));
			} catch (IncorrectInputException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	COMPANY DETAILS
	@GetMapping("/getCompanyDetails/{token}")
	public ResponseEntity<Object> getCompanyDetails(@PathVariable String token)
			throws CouponExistsException, EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CompanyFacade facade = (CompanyFacade) session.getFacade();
			return ResponseEntity.ok(facade.getCompanyDetails());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND-ONE COMPANY
	@GetMapping("/getOneCoupon/{token}/{companyID}")
	public ResponseEntity<Object> getOneCompany(@PathVariable String token, @PathVariable int couponID)
			throws CompanyExistsException, EndOfSessionException {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CompanyFacade facade = (CompanyFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getOneCoupon(couponID));
			} catch (IdDoesntExistsException | IncorrectInputException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

}
