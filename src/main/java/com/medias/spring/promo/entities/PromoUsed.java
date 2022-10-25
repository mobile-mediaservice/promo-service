package com.medias.spring.promo.entities;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "used_promo")
@IdClass(PromoUsedId.class)
public class PromoUsed implements Serializable {
	@Id
	@Column(name = "promo_id", length = 20)
	private String promoId; // id запису 

	@Id
	@Column(name = "gup_id", nullable=false)
	private Long gupId; // id клієнта 
	@Id
	@Column(name = "barcode", length = 15, nullable=false)
	private String barcode; // автор активації промокоду
	@Column(name = "date", nullable=false)
	private Date date; // дата застосування промокоду 

	

	public PromoUsed() {
		super();
	}
	
	public PromoUsed(String promoId, Long gupId, Date date, String barcode) {
		super();
		this.promoId = promoId;
		this.gupId = gupId;
		this.date = date;
		this.barcode = barcode;
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
	@JsonProperty("barcode")
	public String getBarcode() {
		return barcode;
	}
	@JsonProperty("barcode")
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	@JsonProperty("date")
	public Date getDate() {
		return date;
	}
	@JsonProperty("date")
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
