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
	
	// Add one Coupon
	public void addCoupon(Coupon coupon) {
		repo.save(coupon);
	}
	
	// Update one coupon
	public void updateCoupon(Coupon coupon) {
		if(repo.existsById(coupon.getCouponID()))
			repo.save(coupon);
	}
	
	// Delete one coupon
	public void deleteCoupon(long couponId) {
		repo.deleteById(couponId);
	}
	
	// Show one coupon
	public Coupon getOneCoupon(long couponId) {
		Optional<Coupon>opt = repo.findById(couponId);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}
	
	// Show all coupons
	public List<Coupon>getAllCoupons(){
		return repo.findAll();
	}
	
}