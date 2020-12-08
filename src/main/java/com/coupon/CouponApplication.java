package com.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.coupon.job.CouponExpirationDailyJob;

@SpringBootApplication
public class CouponApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CouponApplication.class, args);

		CouponExpirationDailyJob job = ctx.getBean(CouponExpirationDailyJob.class);
		Thread t = new Thread(job);
		t.start();
	}
}
