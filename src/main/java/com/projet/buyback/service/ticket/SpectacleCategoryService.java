package com.projet.buyback.service.ticket;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projet.buyback.model.SpectacleCategory;
import com.projet.buyback.repository.SpectacleCategoryRepository;

@Service
public class SpectacleCategoryService {

	@Autowired
	private SpectacleCategoryRepository spectacleCategoryRepository;
	
	public List<SpectacleCategory> getAllSpectacleCategories() {
		return spectacleCategoryRepository.findAll();
	}

	public Optional<SpectacleCategory> getSpectacleCategoryById(Long id) {
		return spectacleCategoryRepository.findById(id); 
	}
	
	public void deleteSpectacleCategoryById(Long id) {		
		spectacleCategoryRepository.deleteById(id);
	}
}
