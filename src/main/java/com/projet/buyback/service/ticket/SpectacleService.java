package com.projet.buyback.service.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.schema.request.jwt.SpectacleDtoResponse;

@Service
public class SpectacleService {

	@Autowired
	private SpectacleRepository spectacleRepository;

	public List<SpectacleDtoResponse> getAllSpectacleTickets() {
		List<SpectacleDtoResponse> spectacleTicketsDto = new ArrayList<>();

		List<Spectacle> spectacleTickets = spectacleRepository.findAll();
		for (Spectacle spectacle : spectacleTickets) {
			spectacleTicketsDto.add(new SpectacleDtoResponse(spectacle.getId(), spectacle.getName(),
					spectacle.getPrice(), spectacle.getStartDate(), spectacle.getEndDate(), spectacle.getAddress(),
					spectacle.getSpectacleCategory(), spectacle.getUser().getId(), spectacle.getUser().getEmail(),
					spectacle.getUser().getEmail()));
		}
		return spectacleTicketsDto;
	}

	public SpectacleDtoResponse getSpectacleTicketById(Long id) {
		Optional<Spectacle> optSpectacleTicket = spectacleRepository.findById(id);
		SpectacleDtoResponse spectacleDtoResponse = new SpectacleDtoResponse();
		if (optSpectacleTicket.isPresent()) {
			Spectacle spectacleTicket = optSpectacleTicket.get();
			spectacleDtoResponse.setId(spectacleTicket.getId());
			spectacleDtoResponse.setName(spectacleTicket.getName());
			spectacleDtoResponse.setPrice(spectacleTicket.getPrice());
			spectacleDtoResponse.setStartDate(spectacleTicket.getStartDate());
			spectacleDtoResponse.setEndDate(spectacleTicket.getEndDate());
			spectacleDtoResponse.setAddress(spectacleTicket.getAddress());
			spectacleDtoResponse.setCategory(spectacleTicket.getSpectacleCategory());
			spectacleDtoResponse.setUserId(spectacleTicket.getUser().getId());
			spectacleDtoResponse.setUsername(spectacleTicket.getUser().getEmail());
			spectacleDtoResponse.setEmail(spectacleTicket.getUser().getEmail());
		}
		return spectacleDtoResponse;
	}

	public SpectacleDtoResponse createSpectacleTicket(Spectacle spectacleTicket) {
		Spectacle savedSpectacle = spectacleRepository.save(spectacleTicket);
		SpectacleDtoResponse spectacleDtoResponse = new SpectacleDtoResponse();
		spectacleDtoResponse.setId(savedSpectacle.getId());
		spectacleDtoResponse.setName(savedSpectacle.getName());
		spectacleDtoResponse.setPrice(savedSpectacle.getPrice());
		spectacleDtoResponse.setStartDate(savedSpectacle.getStartDate());
		spectacleDtoResponse.setEndDate(savedSpectacle.getEndDate());
		spectacleDtoResponse.setAddress(savedSpectacle.getAddress());
		spectacleDtoResponse.setCategory(savedSpectacle.getSpectacleCategory());
		spectacleDtoResponse.setUserId(savedSpectacle.getUser().getId());
		spectacleDtoResponse.setUsername(savedSpectacle.getUser().getEmail());
		spectacleDtoResponse.setEmail(savedSpectacle.getUser().getEmail());
		return spectacleDtoResponse;
	}

	public void deleteSpectacleTicketById(Long id) {
		spectacleRepository.deleteById(id);

	}
}
