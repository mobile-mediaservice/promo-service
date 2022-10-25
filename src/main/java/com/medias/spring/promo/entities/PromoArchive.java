package com.medias.spring.promo.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "archive_promo")
public class PromoArchive {
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
	
	
	public PromoArchive() {
		super();
	}
	
	public PromoArchive(String promoId, Long gupId,Date dtFrom, Date dtTo, int qtyTotal,int qtyUsed ,String user, Long gupIdReferral) {
		super();
		this.promoId = promoId;
		this.gupId = gupId;
		this.dtFrom = dtFrom;
		this.dtTo = dtTo;
		this.qtyTotal = qtyTotal;
		this.qtyUsed = qtyUsed;
		this.user = user;
		this.gupIdReferral = gupIdReferral;
	}



	

}
