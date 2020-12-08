package com.coupon.exception;

public class IdDoesntExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public IdDoesntExistsException() {
		super("The typed ID number does not exists!");
	}

}
