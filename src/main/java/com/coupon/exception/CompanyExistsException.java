package com.coupon.exception;

public class CompanyExistsException extends Exception{

	private static final long serialVersionUID = 1L;

	public CompanyExistsException() {
		super("The typed company already exists!");
	}
}
