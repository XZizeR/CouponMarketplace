package com.coupon.facade;

import org.springframework.beans.factory.annotation.Autowired;

import com.coupon.db.CompaniesDBDAO;
import com.coupon.db.CouponsDBDAO;
import com.coupon.db.CustomersDBDAO;

public abstract class ClientFacade {
	@Autowired
	protected CompaniesDBDAO compDB;
	@Autowired
	protected CouponsDBDAO coupDB;
	@Autowired
	protected CustomersDBDAO custDB;

	public abstract boolean login(String email, String password);

}
