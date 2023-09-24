package com.projet.buyback.repository.sport;

import com.projet.buyback.model.spectacle.Spectacle;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.sport.Sport;
import java.util.List;
import com.projet.buyback.model.User;


@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

	boolean existsById(Long id);
	List<Sport> findByPurchaser(User user);
	List<Sport> findBySellerAndPurchaserIsNotNull(User user);
	List<Sport> findBySellerAndPurchaserIsNull(User user);

	@Query("SELECT sport FROM Sport sport " +
		"WHERE (:seller IS NULL OR sport.seller != :seller) " +
		"AND sport.purchaser is null "
		+ "ORDER BY sport.startDate ASC")
	List<Sport> findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(@Param("seller") User seller, PageRequest limit);

}
