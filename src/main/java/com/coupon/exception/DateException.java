package com.coupon.exception;

public class DateException extends Exception{

	private static final long serialVersionUID = 1L;

	public DateException() {
		super("The date you chose is incorrect!");
	}
	
}
