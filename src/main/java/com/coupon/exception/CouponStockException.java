package com.coupon.exception;

public class CouponStockException extends Exception{
	/**
	 *  Throw if the coupon's out of stock.
	 */
	private static final long serialVersionUID = 1L;

	public CouponStockException() {
		super("The typed Coupons stock is out!");
	}
}
