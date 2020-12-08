package com.coupon.exception;

public class CouponDateException extends Exception{

	private static final long serialVersionUID = 1L;

	public CouponDateException() {
		super("The typed coupons date is expired!");
	}
}
