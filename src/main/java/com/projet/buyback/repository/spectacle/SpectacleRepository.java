package com.projet.buyback.repository.spectacle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.spectacle.Spectacle;
import java.util.List;
import com.projet.buyback.model.User;


@Repository
public interface SpectacleRepository extends JpaRepository<Spectacle, Long> {

	List<Spectacle> findByForsaleUserId(User user);
}
