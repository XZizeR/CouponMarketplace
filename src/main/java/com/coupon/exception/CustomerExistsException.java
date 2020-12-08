package com.coupon.exception;

public class CustomerExistsException extends Exception{

	private static final long serialVersionUID = 1L;

	public CustomerExistsException() {
		super("The typed Customer already exists!");
	}
}
