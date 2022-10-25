package com.medias.spring.promo.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "active_promo")
public class PromoActive {
	@Id
	@Column(name = "promo_id", length = 20, nullable = false)
	private String promoId; // id запису 
	@Column(name = "gup_id", nullable = false)
	private Long gupId; // id клієнта 
	@Column(name = "dt_from", nullable = false)
	private Date dtFrom; // дата початку дії
	@Column(name = "dt_to", nullable = false)
	private Date dtTo; // дата кінця дії
	@Column(name = "qty_total", nullable = false)
	private int qtyTotal; // загальна к-сть застосувань промокоду 
	@Column(name = "qty_used", nullable = false)
	private int qtyUsed = 0; // к-сть використань промокоду 
	@Column(name = "user", length = 30, nullable = false)
	private String user; // автор активації промокоду
	@Column(name = "gup_id_referral", nullable = true)
	private Long gupIdReferral = null; // клієнт який згенерував промокод
	
	
	public PromoActive() {
		super();
	}
	
	public PromoArchive toArchive() {
		return new PromoArchive(this.promoId,this.gupId,this.dtFrom, this.dtTo, this.qtyTotal, this.qtyUsed, this.user, this.gupIdReferral);
	}
	
	@JsonProperty("promo_id")
	public String getPromoId() {
		return promoId;
	}


	@JsonProperty("promo_id")
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}


	@JsonProperty("gup_id")
	public Long getGupId() {
		return gupId;
	}


	@JsonProperty("gup_id")
	public void setGupId(Long gupId) {
		this.gupId = gupId;
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


	@JsonProperty("qty_total")
	public int getQtyTotal() {
		return qtyTotal;
	}


	@JsonProperty("qty_total")
	public void setQtyTotal(int qtyTotal) {
		this.qtyTotal = qtyTotal;
	}


	@JsonProperty("qty_used")
	public int getQtyUsed() {
		return qtyUsed;
	}


	@JsonProperty("qty_used")
	public void setQtyUsed(int qtyUsed) {
		this.qtyUsed = qtyUsed;
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
	public Long getGupIdReferral() {
		return gupIdReferral;
	}


	@JsonProperty("gup_id_referral")
	public void setGupIdReferral(Long gupIdReferral) {
		this.gupIdReferral = gupIdReferral;
	}



}
