package com.medias.spring.promo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medias.spring.promo.entities.PromoActive;

@Repository
public interface PromoActiveRepository extends JpaRepository<PromoActive, String> {
	List<PromoActive> findByGupId(Long gup_id);
	List<PromoActive> findByPromoIdAndGupId(String promo_id, Long gup_id);
	List<PromoActive> findByGupIdAndDtFromLessThanAndDtToGreaterThan(Long gup_id, Date dt_to, Date dt_from);
	List<PromoActive> findByDtToLessThan(Date now);
}
