package com.coupon.exception;

public class CouponDoesntExistException extends Exception{

	private static final long serialVersionUID = 1L;

	public CouponDoesntExistException() {
		super("The typed coupon does not exist!");
	}
}
