package com.coupon.exception;

public class InvalidClientTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidClientTypeException() {
		super("The typed client type does not exists!");
	}
}