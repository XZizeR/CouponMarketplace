package com.coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupon.beans.Coupon;

public interface CouponsRepository extends JpaRepository<Coupon, Long>{
		
	List<Coupon>findCouponByCompanyID(long id);
}
