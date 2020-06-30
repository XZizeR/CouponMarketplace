package com.coupon.exception;

public class CouponDateException extends Exception{

	/**
	 *  Throw if the coupon is already exists.
	 */
	private static final long serialVersionUID = 1L;

	public CouponDateException() {
		super("The typed Coupons date is expired!");
	}
}
