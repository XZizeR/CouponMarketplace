package com.coupon.exception;

public class IdDoesntExistsException extends Exception {
	
	/**
	 *  Throws if the typed ID number doesn't exists.
	 */
	private static final long serialVersionUID = 1L;

	public IdDoesntExistsException() {
		super("Java: The typed ID number doesn't exists!");
	}

}
