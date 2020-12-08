package com.coupon.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.coupon.exception.InvalidClientTypeException;
import com.coupon.exception.LoginException;
import com.coupon.facade.AdminFacade;
import com.coupon.facade.ClientFacade;
import com.coupon.facade.CompanyFacade;
import com.coupon.facade.CustomerFacade;

@Component
public class LoginManager {

	@Autowired
	private ConfigurableApplicationContext ctx;

//	LOGIN
	public ClientFacade login(String email, String password, ClientType clientType) throws LoginException, InvalidClientTypeException {

		switch (clientType) {
		case Admin:
			AdminFacade admin = ctx.getBean(AdminFacade.class);
			if (admin.login(email, password))
				return admin;
			else
				throw new LoginException();
		case Customer:
			CustomerFacade customer = ctx.getBean(CustomerFacade.class);
			if (customer.login(email, password))
				return customer;
			else
				throw new LoginException();
		case Company:
			CompanyFacade company = ctx.getBean(CompanyFacade.class);
			if (company.login(email, password))
				return company;
			else
				throw new LoginException();
		default:
			throw new InvalidClientTypeException();
		}
	}
}
