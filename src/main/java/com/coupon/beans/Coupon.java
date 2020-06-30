package com.coupon.beans;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Scope("prototype")
@Entity
@Table(name = "Coupons", uniqueConstraints = { @UniqueConstraint(columnNames = "title", name = "uniqueConstraint") })
@NoArgsConstructor // constructor - for hibernate
@AllArgsConstructor // constructor - to show
@Getter
@Setter
//@ToString
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Coupon_ID")
	private long couponID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_ID")
	@JsonIgnore
	private Company companyID;

	@NotNull
	@Min(0)
	@Column(name = "Amount")
	private long amount;

	@Column(name = "Category_ID")
	private Category category;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Title")
	private String title; // unique

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Description")
	private String description;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Image")
	private String image;

	@Column(name = "Start_Date")
	private Date startDate;

	@Column(name = "End_Date")
	private Date endDate;

	@DecimalMin(value = "0.0", inclusive = true)
	@Column(name = "Price")
	private double price;

	// to purchase
	public Coupon(long couponID) {
		super();
		this.couponID = couponID;
	}

	// to create
	public Coupon(Company companyID, @NotNull @Min(0) long amount, Category category,
			@NotBlank(message = "Name may not be blank") String title,
			@NotBlank(message = "Name may not be blank") String description,
			@NotBlank(message = "Name may not be blank") String image, Date startDate, Date endDate,
			@DecimalMin(value = "0.0", inclusive = true) double price) {
		super();
		this.companyID = companyID;
		this.amount = amount;
		this.category = category;
		this.title = title;
		this.description = description;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		Coupon coup = (Coupon) obj;
		return this.getCouponID() == coup.getCouponID();
	}

}