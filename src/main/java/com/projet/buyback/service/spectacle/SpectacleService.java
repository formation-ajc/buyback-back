package com.projet.buyback.service.spectacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.schema.response.spectacle.SpectacleResponse;

@Service
public class SpectacleService {

	@Autowired
	private SpectacleRepository spectacleRepository;

	public List<SpectacleResponse> getAllSpectacleTickets() {
		List<Spectacle> spectacleTickets = spectacleRepository.findAll();
		if(!spectacleTickets.isEmpty()) {
			
			List<SpectacleResponse> spectacleTicketsDto = new ArrayList<>();
			
			for (Spectacle spectacle : spectacleTickets) {
				spectacleTicketsDto.add(SpectacleResponse.createSpectacleResponse(spectacle));
			}
			return spectacleTicketsDto;
		}else {
			return null;
		}
	}

	public SpectacleResponse getSpectacleTicketById(Long id) {
		Optional<Spectacle> optSpectacleTicket = spectacleRepository.findById(id);
		if (optSpectacleTicket.isPresent()) {
			Spectacle spectacleTicket = optSpectacleTicket.get();
			return SpectacleResponse.createSpectacleResponse(spectacleTicket);
		} else {
			return null;
		}
	}

	public SpectacleResponse createSpectacleTicket(Spectacle spectacleTicket) {
		Spectacle savedSpectacle = spectacleRepository.save(spectacleTicket);
        return SpectacleResponse.createSpectacleResponse(savedSpectacle);
    }

	public void deleteSpectacleTicketById(Long id) {
		spectacleRepository.deleteById(id);

	}
}