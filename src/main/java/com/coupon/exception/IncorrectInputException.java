package com.coupon.exception;

public class IncorrectInputException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public IncorrectInputException() {
		super("Incorrect input: The number can not be negative!");
	}

}
