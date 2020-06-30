package com.coupon.exception;

public class CustomerExistsException extends Exception{

	/**
	 *  Throw if the customer is already exists.
	 */
	private static final long serialVersionUID = 1L;

	public CustomerExistsException() {
		super("The typed Customer exists already!");
	}
}
