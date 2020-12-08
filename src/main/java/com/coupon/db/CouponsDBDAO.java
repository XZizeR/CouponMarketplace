package com.coupon.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coupon.beans.Coupon;
import com.coupon.repository.CouponsRepository;

@Repository
public class CouponsDBDAO {

	@Autowired
	private CouponsRepository repo;
	
	public CouponsDBDAO() {};
	
//	CREATE COUPON
	public void addCoupon(Coupon coupon) {
		repo.save(coupon);
	}
	
//	UPDATE COUPON
	public void updateCoupon(Coupon coupon) {
		if(repo.existsById(coupon.getCouponID()))
			repo.save(coupon);
	}
	
//	DELETE COUPON
	public void deleteCoupon(long couponId) {
		repo.deleteById(couponId);
	}
	
//	GET-ONE COUPON
	public Coupon getOneCoupon(long couponId) {
		Optional<Coupon>opt = repo.findById(couponId);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}
	
//	GET-ALL COUPONS
	public List<Coupon>getAllCoupons(){
		return repo.findAll();
	}
	
}