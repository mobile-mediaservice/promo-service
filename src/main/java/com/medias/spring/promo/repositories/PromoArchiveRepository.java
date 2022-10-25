
package com.medias.spring.promo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medias.spring.promo.entities.PromoArchive;

@Repository
public interface PromoArchiveRepository extends JpaRepository<PromoArchive, String> {

}
