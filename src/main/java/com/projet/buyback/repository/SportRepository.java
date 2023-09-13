package com.projet.buyback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.Sport;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

	boolean existsById(Long id);
}
