package com.coupon.exception;

public class EmptyFieldException extends Exception {
	/**
	 *  Throws if one of the fields are empty at creating an object.
	 */
	private static final long serialVersionUID = 1L;

	public EmptyFieldException() {
		super("One of the entered fields is empty!");
	}	

}
