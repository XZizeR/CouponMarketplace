package com.coupon.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.beans.Category;
import com.coupon.exception.CouponDateException;
import com.coupon.exception.CouponDoesntExistException;
import com.coupon.exception.CouponPurchasedException;
import com.coupon.exception.CouponStockException;
import com.coupon.exception.IdDoesntExistsException;
import com.coupon.exception.IncorrectInputException;
import com.coupon.facade.CustomerFacade;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

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

//	PURCHASE COUPON
	@PostMapping("/purchaseCoupon/{token}/{couponID}")
	public ResponseEntity<Object> purchaseCoupon(@PathVariable String token, @PathVariable int couponID) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CustomerFacade facade = (CustomerFacade) session.getFacade();
			try {
				facade.purchaseCoupon(couponID);
				return ResponseEntity.ok(couponID);
			} catch (CouponPurchasedException | CouponDateException | CouponDoesntExistException | CouponStockException
					| IncorrectInputException | IdDoesntExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND COUPONS
	@GetMapping("/getCustomerCoupons/{token}")
	public ResponseEntity<Object> getCustomerCoupons(@PathVariable String token) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CustomerFacade facade = (CustomerFacade) session.getFacade();
			return ResponseEntity.ok(facade.getCustomerCoupons());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND COUPONS - CATEGORY
	@GetMapping("/getCustomerCouponsByCategory/{token}/{category}")
	public ResponseEntity<Object> getCustomerCoupons(@PathVariable String token, @PathVariable Category category) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CustomerFacade facade = (CustomerFacade) session.getFacade();
			return ResponseEntity.ok(facade.getCustomerCoupons(category));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND COUPONS - PRICE LIMIT
	@GetMapping("/getCustomerCouponsByMaxprice/{token}/{maxPrice}")
	public ResponseEntity<Object> getCustomerCoupons(@PathVariable String token, @PathVariable int maxPrice) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CustomerFacade facade = (CustomerFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getCustomerCoupons(maxPrice));
			} catch (IncorrectInputException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	CUSTOMER DETAILS
	@GetMapping("/getCustomerDetails/{token}")
	public ResponseEntity<Object> getCustomerDetails(@PathVariable String token) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CustomerFacade facade = (CustomerFacade) session.getFacade();
			return ResponseEntity.ok(facade.getCustomerDetails());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

//	FIND-ALL COUPONS
	@GetMapping("/getAllCoupons/{token}")
	public ResponseEntity<Object> getAllCoupons(@PathVariable String token) {
		Session session = sessionMap.get(token);
		if (session != null)
			isTimeOut(token, session);
		if (session != null) {
			CustomerFacade facade = (CustomerFacade) session.getFacade();
			return ResponseEntity.ok(facade.getAllCoupons());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

}
