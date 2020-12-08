package com.coupon.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coupon.beans.Category;
import com.coupon.beans.Coupon;
import com.coupon.beans.Customer;
import com.coupon.exception.CouponDateException;
import com.coupon.exception.CouponDoesntExistException;
import com.coupon.exception.CouponPurchasedException;
import com.coupon.exception.CouponStockException;
import com.coupon.exception.IdDoesntExistsException;
import com.coupon.exception.IncorrectInputException;

@Service
public class CustomerFacade extends ClientFacade {

	private long customerID;

//	LOGIN - CUSTOMER
	@Override
	public boolean login(String email, String password) {
		Customer temp = custDB.isCustomerExist(email, password);
		if (temp != null) {
			customerID = temp.getCustomerID();
			return true;
		}
		return false;
	}

//	PURCHASE COUPON
	public void purchaseCoupon(int couponID) throws CouponPurchasedException, CouponDateException, CouponStockException,
			CouponDoesntExistException, IncorrectInputException, IdDoesntExistsException {
		// check - negative number
		if (couponID < 0) {
			throw new IncorrectInputException();
		}

		Customer customer = custDB.getOneCustomer(customerID);
		Coupon coupon = coupDB.getOneCoupon(couponID);
		Date today = new Date();
		List<Coupon> customerCoupons = customer.getCoupons();

//		checks - coupon exist
		if (coupon != null) {
			System.out.println(coupon);
		} else {
			throw new CouponDoesntExistException();
		}
		for (Coupon coup : customerCoupons) {
			if (coup.getCouponID() == couponID) {
				throw new CouponPurchasedException();
			}
		}
//		check - the amount
		if (coupon.getAmount() == 0) {
			throw new CouponStockException();
		}
//		check - the date
		if (coupon.getEndDate().before(today)) {
			throw new CouponDateException();
		}

//		purchase
		customer.getCoupons().add(coupon);
		custDB.updateCustomer(customer);

//		amount update
		coupon.setAmount(coupon.getAmount() - 1);
		coupDB.updateCoupon(coupon);
	}

//	GET CUSTOMER COUPONS
	public List<Coupon> getCustomerCoupons() {
		List<Coupon> coupons = custDB.getOneCustomer(customerID).getCoupons();
		return coupons;
	}

//	GET CUSTOMER COUPONS - CATEGORY
	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : custDB.getOneCustomer(customerID).getCoupons()) {
			if (coup.getCategory().equals(category))
				coupons.add(coup);
		}
		return coupons;
	}

//	GET CUSTOMER COUPONS - PRICE LIMIT
	public List<Coupon> getCustomerCoupons(double maxPrice) throws IncorrectInputException {
//		check - negative number
		if (maxPrice < 0) {
			throw new IncorrectInputException();
		}

		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : custDB.getOneCustomer(customerID).getCoupons()) {
			if (coup.getPrice() <= maxPrice)
				coupons.add(coup);
		}
		return coupons;
	}

//	CUSTOMER DETAILS
	public Customer getCustomerDetails() {
		return custDB.getOneCustomer(customerID);
	}

//	GET-ALL COUPONS
	public List<Coupon> getAllCoupons() {
		List<Coupon> list = new ArrayList<>();
		List<Coupon> coupons = coupDB.getAllCoupons();
		for (Coupon coup : coupons)
			list.add(coup);
		return list;
	}

}