package com.coupon.exception;

public class CouponPurchasedException extends Exception{

	private static final long serialVersionUID = 1L;

	public CouponPurchasedException() {
		super("The typed coupon already purchased!");
	}
}
