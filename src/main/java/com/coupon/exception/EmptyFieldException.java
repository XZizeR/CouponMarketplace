package com.coupon.exception;

public class EmptyFieldException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyFieldException() {
		super("One of the entered fields - is empty!");
	}	

}
