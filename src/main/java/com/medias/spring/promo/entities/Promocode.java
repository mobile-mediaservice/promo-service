package com.medias.spring.promo.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "promocodes")
public class Promocode {
	@Id
	@Column(name = "id", length = 20)
	private String id; // id запису 
	@Column(name = "gup_id", nullable = true)
	private Long gupId; // id клієнта 
	@Column(name = "is_activated", nullable= false)
	private boolean isActivated; // відмітка про активацію
	@Column(name = "qty", nullable= false)
	private int qty; // кількість використань 
	@Column(name = "dt_from", nullable= false)
	private Date dtFrom; // дата початку 
	@Column(name = "dt_to", nullable= false)
	private Date dtTo; // дата кінця 
	@Column(name = "user", length = 30, nullable= false)
	private String user; // користувач який створив промокод
	@Column(name = "gup_id_referral", nullable= false)
	private long gupIdReferral; // клієнт який згенерував промокод
	@Column(name = "discount_percentage", nullable= false)
	private int discountPercentage; // відсоток знижки 
	@Column(name = "discount_amount", nullable= false)
	private float discountAmount; // сума знижки 
	@Column(name = "description", length = 100, nullable = true)
	private String description; // опис промокоду
	
	public Promocode() {
		super();
	}
	
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("gup_id")
	public Long getGupId() {
		return gupId;
	}
	
	@JsonProperty("gup_id")
	public void setGupId(Long gupId) {
		this.gupId = gupId;
	}
	
	@JsonProperty("is_activated")
	public boolean isActivated() {
		return isActivated;
	}
	@JsonProperty("is_activated")
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	@JsonProperty("qty")
	public int getQty() {
		return qty;
	}
	@JsonProperty("qty")
	public void setQty(int qty) {
		this.qty = qty;
	}
	@JsonProperty("dt_from")
	public Date getDtFrom() {
		return dtFrom;
	}
	@JsonProperty("dt_from")
	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}
	@JsonProperty("dt_to")
	public Date getDtTo() {
		return dtTo;
	}
	@JsonProperty("dt_to")
	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}
	@JsonProperty("user")
	public String getUser() {
		return user;
	}
	@JsonProperty("user")
	public void setUser(String user) {
		this.user = user;
	}
	@JsonProperty("gup_id_referral")
	public long getGupIdReferral() {
		return gupIdReferral;
	}
	@JsonProperty("gup_id_referral")
	public void setGupIdReferral(long gupIdReferral) {
		this.gupIdReferral = gupIdReferral;
	}
	@JsonProperty("discount_percentage")
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	@JsonProperty("discount_percentage")
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	@JsonProperty("discount_amount")
	public float getDiscountAmount() {
		return discountAmount;
	}
	@JsonProperty("discount_amount")
	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}
	
}
