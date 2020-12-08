package com.coupon.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.coupon.beans.Category;
import com.coupon.beans.Company;
import com.coupon.beans.Coupon;
import com.coupon.beans.Customer;
import com.coupon.exception.CouponExistsException;
import com.coupon.exception.DateException;
import com.coupon.exception.EmptyFieldException;
import com.coupon.exception.IdDoesntExistsException;
import com.coupon.exception.IncorrectInputException;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {

	private long companyId;

//	LOGIN - COMPANY
	@Override
	public boolean login(String email, String password) {
		Company temp = compDB.isCompanyExist(email, password);
		if (temp != null) {
			companyId = temp.getCompanyID();
			return true;
		}
		return false;
	}

//	ADD COUPON
	public void addCoupon(Coupon coupon)
			throws CouponExistsException, EmptyFieldException, DateException, IncorrectInputException {
//		check - negative number
		if (coupon.getAmount() < 0 || coupon.getPrice() < 0) {
			throw new IncorrectInputException();
		}
//		check - empty fields
		if (coupon.getCategory() == null || coupon.getTitle() == null || coupon.getDescription() == null
				|| coupon.getImage() == null || coupon.getStartDate() == null || coupon.getEndDate() == null) {
			throw new EmptyFieldException();
		}
//		check - the title already exist
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (coup.getTitle().equals(coupon.getTitle()))
				throw new CouponExistsException();
		}
//		check - the date
		if (coupon.getStartDate().getTime() > coupon.getEndDate().getTime()) {
			throw new DateException();
		}

		coupDB.addCoupon(coupon);
	}

//	UPDATE COUPON
	public void updateCoupon(Coupon coupon) throws EmptyFieldException, IdDoesntExistsException, CouponExistsException,
			DateException, IncorrectInputException {
//		check - negative number
		if (coupon.getCouponID() < 0 || coupon.getAmount() < 0 || coupon.getPrice() < 0) {
			throw new IncorrectInputException();
		}
//		check - empty fields
		if (coupon.getCategory() == null || coupon.getTitle() == null || coupon.getDescription() == null
				|| coupon.getImage() == null || coupon.getStartDate() == null || coupon.getEndDate() == null) {
			throw new EmptyFieldException();
		}
//		check - the object exist
		if (coupDB.getOneCoupon(coupon.getCouponID()) == null) {
			throw new IdDoesntExistsException();
		}
//		check - if the title already exist
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (coup.getTitle().equals(coupon.getTitle()))
				throw new CouponExistsException();
		}
//		check - the date
		if (coupon.getStartDate().getTime() > coupon.getEndDate().getTime()) {
			throw new DateException();
		}

		coupDB.updateCoupon(coupon);
	}

//	DELETE COUPON
	public void deleteCoupon(int couponID) throws IdDoesntExistsException, IncorrectInputException {
//		check - negative number
		if (couponID < 0) {
			throw new IncorrectInputException();
		}
//		check - the object exist
		if (coupDB.getOneCoupon(couponID) == null) {
			throw new IdDoesntExistsException();
		}

//		delete from the company
		Coupon couponToDelete = coupDB.getOneCoupon(couponID);
		Company comp = couponToDelete.getCompanyID();
		comp.removeCoupon(couponToDelete);
		compDB.updateCompany(comp);
//		delete from the customer
		List<Customer> customers = custDB.getAllCustomers();
		for (Customer customer : customers) {
			if (customer.getCoupons().contains(couponToDelete)) {
				customer.getCoupons().remove(couponToDelete);
				custDB.updateCustomer(customer);
			}
		}
		coupDB.deleteCoupon(couponID);
	}

//	GET COUPONS
	public List<Coupon> getCompanyCoupons() {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (companyId == coup.getCompanyID().getCompanyID())
				coupons.add(coup);
		}
		return coupons;
	}

//	GET COUPONS - CATEGORY
	public List<Coupon> getCompanyCoupons(Category category) throws EmptyFieldException {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (companyId == coup.getCompanyID().getCompanyID())
				if (coup.getCategory().equals(category))
					coupons.add(coup);
		}
		return coupons;
	}


//	GET COUPONS - PRICE LIMIT
	public List<Coupon> getCompanyCoupons(double maxPrice) throws IncorrectInputException {
		// check - negative number
		if (maxPrice < 0) {
			throw new IncorrectInputException();
		}

		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (companyId == coup.getCompanyID().getCompanyID())
				if (coup.getPrice() <= maxPrice)
					coupons.add(coup);
		}
		return coupons;
	}


//	COMPANY DETAILS
	public Company getCompanyDetails() {
		return compDB.getOneCompany(companyId);
	}

//	GET-ONE COUPON
	public Coupon getOneCoupon(int couponID) throws IdDoesntExistsException, IncorrectInputException {
		// check - negative number
		if (couponID < 0) {
			throw new IncorrectInputException();
		}
		// check - the object exist
		if (coupDB.getOneCoupon(couponID) == null) {
			throw new IdDoesntExistsException();
		}

		return coupDB.getOneCoupon(couponID);
	}
	
}