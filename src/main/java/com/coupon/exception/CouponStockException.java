package com.coupon.exception;

public class CouponStockException extends Exception{

	private static final long serialVersionUID = 1L;

	public CouponStockException() {
		super("The typed coupons stock - is out!");
	}
}
