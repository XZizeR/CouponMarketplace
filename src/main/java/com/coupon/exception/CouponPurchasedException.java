package com.coupon.exception;

public class CouponPurchasedException extends Exception{

	/**
	 *  Throw if the coupon is purchased already.
	 */
	private static final long serialVersionUID = 1L;

	public CouponPurchasedException() {
		super("The typed Coupon purchased already!");
	}
}
