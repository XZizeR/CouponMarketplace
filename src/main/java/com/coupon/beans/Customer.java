package com.coupon.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "Customers", uniqueConstraints = { @UniqueConstraint(columnNames = "email", name = "uniqueConstraint") })
@NoArgsConstructor // constructor - for hibernate
@AllArgsConstructor // constructor - to show
@Data // getters + setters + to-string
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Customer_ID", nullable = false)
	private long customerID;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "First_Name")
	private String firstName;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Last_Name")
	private String lastName;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Email") // unique
	private String email;

	@NotBlank(message = "Name may not be blank")
	@Column(name = "Password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Coupon> coupons;

	// to create
	public Customer(@NotBlank(message = "Name may not be blank") String firstName,
			@NotBlank(message = "Name may not be blank") String lastName,
			@NotBlank(message = "Name may not be blank") String email,
			@NotBlank(message = "Name may not be blank") String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	// to update
	public Customer(long customerID, @NotBlank(message = "Name may not be blank") String firstName,
			@NotBlank(message = "Name may not be blank") String lastName,
			@NotBlank(message = "Name may not be blank") String email,
			@NotBlank(message = "Name may not be blank") String password) {
		super();
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

}