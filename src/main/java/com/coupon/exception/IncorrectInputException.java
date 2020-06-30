package com.coupon.exception;

public class IncorrectInputException extends Exception {
	
	/**
	 *  Throws if the typed number is negative.
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectInputException() {
		super("Incorrect input: Number cant be negative!");
	}

}
