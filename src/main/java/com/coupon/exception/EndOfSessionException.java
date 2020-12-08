package com.coupon.exception;

public class EndOfSessionException extends Exception{

	private static final long serialVersionUID = 1L;

	public EndOfSessionException() {
		super("Your session has ended!");
	}
}
