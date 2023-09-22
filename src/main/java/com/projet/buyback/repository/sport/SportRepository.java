package com.projet.buyback.repository.sport;

import com.projet.buyback.model.spectacle.Spectacle;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
