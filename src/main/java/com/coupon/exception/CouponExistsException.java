package com.coupon.exception;

public class CouponExistsException extends Exception{

	private static final long serialVersionUID = 1L;

	public CouponExistsException() {
		super("The typed coupon already exists!");
	}
}
