package com.coupon.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coupon.beans.Company;
import com.coupon.beans.Coupon;
import com.coupon.beans.Customer;
import com.coupon.db.CompaniesDBDAO;
import com.coupon.db.CouponsDBDAO;
import com.coupon.db.CustomersDBDAO;

/*	
 * deletes multiple outdated coupons.
 */

@Component
public class CouponExpirationDailyJob implements Runnable {

	// Fields
	private boolean quit = false;
	@Autowired
	protected CompaniesDBDAO compDB;
	@Autowired
	protected CouponsDBDAO coupDB;
	@Autowired
	protected CustomersDBDAO custDB;

	// Run
	// 1. get all coupons
	// 2. check the date
	// 3. if it's expired remove it from the customer list
	// 4. remove the coupon from the company list
	// 5. delete the coupon
	@Override
	public void run() {
		while (!quit) {
			try {
			Date today = new Date();
			// get all coupons
			List<Coupon> AllCoupons = coupDB.getAllCoupons();
			List<Coupon> trash = new ArrayList<Coupon>();
			for (Coupon coup : AllCoupons) {
					// check the date - look for expired coupons
					if (coup.getEndDate().before(today)) {
						// remove the coupon from customers list
						removeFromCustomer(coup);
						// remove the coupon from companies list
						removeFromCompany(coup);
						// delete coupon
						trash.add(coup);
					}
				}
				for (Coupon coup : trash) {
					System.out.println("deleted coupon: " + coup.getCouponID());
					AllCoupons.remove(coup);
					coupDB.deleteCoupon(coup.getCouponID());
				}
				// to the next time
				System.out.println("Successfully launched CouponExpirationDailyJob");
				TimeUnit.HOURS.sleep(24);
 			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
	}

	public void removeFromCustomer(Coupon coup) {
		List<Customer> customers = custDB.getAllCustomers();
		for (Customer cust : customers) {
			List<Coupon> custCoupons = cust.getCoupons();
			custCoupons.remove(coup);
			custDB.updateCustomer(cust);
		}
	}

	public void removeFromCompany(Coupon coup) {
		List<Company> companies = compDB.getAllCompanies();
		for (Company comp : companies) {
			List<Coupon> compCoupons = comp.getCoupons();
			compCoupons.remove(coup);
			compDB.updateCompany(comp);
		}
	}

	// Stop
	public void stop() {
		quit = true;
	}
}