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

	// Login into the system as Company and return Id
	@Override
	public boolean login(String email, String password) {
		Company temp = compDB.isCompanyExist(email, password);
		if (temp != null) {
			companyId = temp.getCompanyID();
			return true;
		}
		return false;
	}

	// Add one Coupon
	public void addCoupon(Coupon coupon)
			throws CouponExistsException, EmptyFieldException, DateException, IncorrectInputException {
		// check if the number is negative
		if (coupon.getAmount() < 0 || coupon.getPrice() < 0) {
			throw new IncorrectInputException();
		}
		// check - if the fields empty.
		if (coupon.getCategory() == null || coupon.getTitle() == null || coupon.getDescription() == null
				|| coupon.getImage() == null || coupon.getStartDate() == null || coupon.getEndDate() == null) {
			throw new EmptyFieldException();
		}
		// check - if the object already exist.
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (coup.getTitle().equals(coupon.getTitle()))
				throw new CouponExistsException();
		}
		// check - date.
		if (coupon.getStartDate().getTime() > coupon.getEndDate().getTime()) {
			throw new DateException();
		}

		// the creation
		coupDB.addCoupon(coupon);
	}

	// Update one Coupon
	public void updateCoupon(Coupon coupon) throws EmptyFieldException, IdDoesntExistsException, CouponExistsException,
			DateException, IncorrectInputException {
		// check if the number is negative
		if (coupon.getCouponID() < 0 || coupon.getAmount() < 0 || coupon.getPrice() < 0) {
			throw new IncorrectInputException();
		}
		// check - if the fields empty.
		if (coupon.getCategory() == null || coupon.getTitle() == null || coupon.getDescription() == null
				|| coupon.getImage() == null || coupon.getStartDate() == null || coupon.getEndDate() == null) {
			throw new EmptyFieldException();
		}
		// check - if the object exist
		if (coupDB.getOneCoupon(coupon.getCouponID()) == null) {
			throw new IdDoesntExistsException();
		}
		// check - if the object already exist.
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (coup.getTitle().equals(coupon.getTitle()))
				throw new CouponExistsException();
		}
		// check - date.
		if (coupon.getStartDate().getTime() > coupon.getEndDate().getTime()) {
			throw new DateException();
		}

		// the update
		coupDB.updateCoupon(coupon);
	}

	// Delete one Coupon
	// 1. delete the coupon from the customers
	// 2. delete the coupon from the companies
	// 3. delete coupon
	public void deleteCoupon(int couponID) throws IdDoesntExistsException, IncorrectInputException {
		// check if the number is negative
		if (couponID < 0) {
			throw new IncorrectInputException();
		}
		// check - if the object exist
		if (coupDB.getOneCoupon(couponID) == null) {
			throw new IdDoesntExistsException();
		}

		// disconnect it from the company
		Coupon couponToDelete = coupDB.getOneCoupon(couponID);
		Company comp = couponToDelete.getCompanyID();
		comp.removeCoupon(couponToDelete);
		compDB.updateCompany(comp);
		// disconnect it from the customer
		List<Customer> customers = custDB.getAllCustomers();
		for (Customer customer : customers) {
			if (customer.getCoupons().contains(couponToDelete)) {
				customer.getCoupons().remove(couponToDelete);
				custDB.updateCustomer(customer);
			}
		}

		// delete the coupon
		coupDB.deleteCoupon(couponID);

//		List<Customer> customers = custDB.getAllCustomers();
//		for (Customer cust : customers) {
//			List<Coupon> coupons = cust.getCoupons();
//			for (Coupon coup : coupons) {
//				if(couponID==coup.getCouponID())
//					coupons.remove(coup);
//			}
//		}
//		List<Company>companies=compDB.getAllCompanies();
//		for (Company comp : companies) {
//			List<Coupon> coupons = coupDB.getAllCoupons();
//			for (Coupon coup : coupons) {
//				if(couponID==coup.getCouponID())
//					coupons.remove(couponID);
//			}
//			compDB.updateCompany(comp);
//		}
//		
//		coupDB.deleteCoupon(couponID);
	}

	// getCompanyCoupons #1 - all coupons of the company
	public List<Coupon> getCompanyCoupons() {
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (companyId == coup.getCompanyID().getCompanyID())
				coupons.add(coup);
		}
		return coupons;
	}

	// getCompanyCoupons #2 - by category
	public List<Coupon> getCompanyCoupons(Category category) throws EmptyFieldException {
		// the search
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (companyId == coup.getCompanyID().getCompanyID())
				if (coup.getCategory().equals(category))
					coupons.add(coup);
		}
		return coupons;
	}

	// getCompanyCoupons #3 - by max price
	public List<Coupon> getCompanyCoupons(double maxPrice) throws IncorrectInputException {
		// check if the number is negative
		if (maxPrice < 0) {
			throw new IncorrectInputException();
		}

		// the search
		List<Coupon> coupons = new ArrayList<>();
		for (Coupon coup : coupDB.getAllCoupons()) {
			if (companyId == coup.getCompanyID().getCompanyID())
				if (coup.getPrice() <= maxPrice)
					coupons.add(coup);
		}
		return coupons;
	}

	// getCompanyDetails
	public Company getCompanyDetails() {
		return compDB.getOneCompany(companyId);
	}

	// getOneCoupon
	public Coupon getOneCoupon(int couponID) throws IdDoesntExistsException, IncorrectInputException {
		// check if the number is negative
		if (couponID < 0) {
			throw new IncorrectInputException();
		}
		// check - if the object exist
		if (coupDB.getOneCoupon(couponID) == null) {
			throw new IdDoesntExistsException();
		}

		// the search
		return coupDB.getOneCoupon(couponID);
	}

}