package com.coupon.exception;

public class CouponExistsException extends Exception{

	/**
	 *  Throw if the coupon is already exists.
	 */
	private static final long serialVersionUID = 1L;

	public CouponExistsException() {
		super("The typed Coupon exists already!");
	}
}
