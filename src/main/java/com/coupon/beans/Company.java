package com.coupon.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Scope("prototype")
@Entity
@Table(name = "Companies", uniqueConstraints = { @UniqueConstraint(columnNames = "email", name = "uniqueConstraint") })
@NoArgsConstructor // constructor - for hibernate
@AllArgsConstructor // constructor - to show
@Data // getters + setters + to-string
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // this will be an identity column
	@Column(name = "Company_ID")
	private long companyID;

	@NotBlank(message = "Name may not be blank") // the input is n
	@Column(name = "Name")
	private String name;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Email")
	private String email;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Password")
	private String password;

	@OneToMany(mappedBy = "companyID", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Coupon> coupons;

	// to create
	public Company(@NotBlank(message = "Name may not be blank") String name,
			@NotBlank(message = "Name may not be blank") String email,
			@NotBlank(message = "Name may not be blank") String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	// to update
	public Company(long companyID, @NotBlank(message = "Name may not be blank") String name,
			@NotBlank(message = "Name may not be blank") String email,
			@NotBlank(message = "Name may not be blank") String password) {
		super();
		this.companyID = companyID;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	// establishes the foreign key
	public void addCoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}

	// disables the foreign key
	public void removeCoupon(Coupon coupon) {
		this.coupons.remove(coupon);
	}



}