package com.projet.buyback.repository.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.sport.Sport;
import java.util.List;
import com.projet.buyback.model.User;


@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

	boolean existsById(Long id);
	List<Sport> findBySeller(User user);
	List<Sport> findByPurchaser(User user);
}
