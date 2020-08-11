package com.xyz.ratemanagement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "rate")
public class Rate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="RATE_ID")
	private  Long rateId;
	
	@Column(name="RATE_DESCRIPTION")
	private  String rateDescription;
	
	@NotNull(message="Please provide Rate Effective")
	@Column(name="RATE_EFFECTIVE_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private  Date rateEffectiveDate ;
	
	@NotNull(message="Please provide Rate Expiration")
	@Column(name="RATE_EXPIRATION_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private  Date rateExpirationDate;
	
	@NotNull(message="Please provide amount")
	@Column(name="AMOUNT")
	@DecimalMax(value = "9999", inclusive = false)
	private  Double amount;
	
	@Transient
	private String surchargeRate;
	
	public Rate() {
			
		}
	
	public Rate(String rateDescription, @NotNull(message = "Please provide Rate Effective") Date rateEffectiveDate,
			@NotNull(message = "Please provide Rate Expiration") Date rateExpirationDate,
			@NotNull(message = "Please provide amount") Double amount) {
		super();
		this.rateDescription = rateDescription;
		this.rateEffectiveDate = rateEffectiveDate;
		this.rateExpirationDate = rateExpirationDate;
		this.amount = amount;
	}


	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public String getRateDescription() {
		return rateDescription;
	}

	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}

	public Date getRateEffectiveDate() {
		return rateEffectiveDate;
	}

	public void setRateEffectiveDate(Date rateEffectiveDate) {
		this.rateEffectiveDate = rateEffectiveDate;
	}

	public Date getRateExpirationDate() {
		return rateExpirationDate;
	}

	public void setRateExpirationDate(Date rateExpirationDate) {
		this.rateExpirationDate = rateExpirationDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSurchargeRate() {
		return surchargeRate;
	}

	public void setSurchargeRate(String surchargeRate) {
		this.surchargeRate = surchargeRate;
	}
	
	
	
}
