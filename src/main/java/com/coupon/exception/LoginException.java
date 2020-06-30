package com.coupon.exception;

public class LoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginException() {
		super("Incorrect login input!");
	}

	public LoginException(String message) {
		super(message);
	}
}
