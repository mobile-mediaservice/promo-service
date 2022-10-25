package com.medias.spring.promo.entities;

import java.io.Serializable;
import java.util.Objects;

public class PromoUsedId implements Serializable {

	private String promoId; // id запису 
	private Long gupId; // id клієнта 
	private String barcode; // автор активації промокоду

    public PromoUsedId() {
    }

    public PromoUsedId(String promoId, Long gupId, String barcode) {
        this.promoId = promoId;
        this.gupId = gupId;
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoUsedId promoUsedId = (PromoUsedId) o;
        return promoId.equals(promoUsedId.promoId) &&
        		gupId.equals(promoUsedId.gupId)&& 
        		barcode.equals(promoUsedId.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promoId, gupId, barcode);
    }
}