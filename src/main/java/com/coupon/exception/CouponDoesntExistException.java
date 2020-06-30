package com.coupon.exception;

public class CouponDoesntExistException extends Exception{

	/**
	 *  Throw if the coupon is already exists.
	 */
	private static final long serialVersionUID = 1L;

	public CouponDoesntExistException() {
		super("The typed Coupon doesn't exist!");
	}
}
