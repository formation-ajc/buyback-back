package com.projet.buyback.service.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.buyback.model.User;
import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.schema.request.jwt.SpectacleDtoResponse;
import com.projet.buyback.schema.request.jwt.SportDtoResponse;

@Service
public class SpectacleService {

	@Autowired
	private SpectacleRepository spectacleRepository;

	public List<SpectacleDtoResponse> getAllSpectacleTickets() {
		List<Spectacle> spectacleTickets = spectacleRepository.findAll();
		if(!spectacleTickets.isEmpty()) {
			
			List<SpectacleDtoResponse> spectacleTicketsDto = new ArrayList<>();
			
			for (Spectacle spectacle : spectacleTickets) {
				String purshaseUserEmail = null;
				if(spectacle.getPurshaseUserId() != null) {
					purshaseUserEmail = spectacle.getForsaleUserId().getEmail();
				}
				spectacleTicketsDto.add(new SpectacleDtoResponse(spectacle.getId(), spectacle.getName(),
						spectacle.getPrice(), spectacle.getStartDate(), spectacle.getEndDate(), spectacle.getAddress(),
						spectacle.getSpectacleCategory(), spectacle.getForsaleUserId().getId(), spectacle.getForsaleUserId().getEmail(), spectacle.getForsaleUserId().getFirstname(), spectacle.getForsaleUserId().getLastname(), purshaseUserEmail));
			}
			return spectacleTicketsDto;
		}else {
			return null;
		}
	}
	
	public List<SpectacleDtoResponse> getAllSpectacleTicketsByForsaleUser(User user) {
		List<Spectacle> spectacleTicketsByForsaleUser = spectacleRepository.findByForsaleUserId(user);
		if (!spectacleTicketsByForsaleUser.isEmpty()) {
			List<SpectacleDtoResponse> spectacleTicketsDto = new ArrayList<>();
			for (Spectacle spectacle : spectacleTicketsByForsaleUser) {
				String purshaseUserEmail = null;
				if (spectacle.getPurshaseUserId() != null) {
					purshaseUserEmail = spectacle.getForsaleUserId().getEmail();
				}
				spectacleTicketsDto.add(new SpectacleDtoResponse(spectacle.getId(), spectacle.getName(), spectacle.getPrice(),
						spectacle.getStartDate(), spectacle.getEndDate(), spectacle.getAddress(), spectacle.getSpectacleCategory(),
						spectacle.getForsaleUserId().getId(), spectacle.getForsaleUserId().getEmail(),
						spectacle.getForsaleUserId().getFirstname(), spectacle.getForsaleUserId().getLastname(),
						purshaseUserEmail));
			}
			return spectacleTicketsDto;
		} else {
			return null;
		}
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
			spectacleDtoResponse.setUserId(spectacleTicket.getForsaleUserId().getId());
			spectacleDtoResponse.setEmail(spectacleTicket.getForsaleUserId().getEmail());
			spectacleDtoResponse.setFirstName(spectacleTicket.getForsaleUserId().getFirstname());
			spectacleDtoResponse.setLastName(spectacleTicket.getForsaleUserId().getLastname());
			if(spectacleTicket.getPurshaseUserId() != null) {
				spectacleDtoResponse.setPurchaseUserEmail(spectacleTicket.getPurshaseUserId().getEmail());
			}
			return spectacleDtoResponse;
		} else {
			return null;
		}
	}

	public SpectacleDtoResponse createSpectacleTicket(Spectacle spectacleTicket) {
		Spectacle savedSpectacle = spectacleRepository.save(spectacleTicket);
		if (savedSpectacle != null) {
			SpectacleDtoResponse spectacleDtoResponse = new SpectacleDtoResponse();
			spectacleDtoResponse.setId(savedSpectacle.getId());
			spectacleDtoResponse.setName(savedSpectacle.getName());
			spectacleDtoResponse.setPrice(savedSpectacle.getPrice());
			spectacleDtoResponse.setStartDate(savedSpectacle.getStartDate());
			spectacleDtoResponse.setEndDate(savedSpectacle.getEndDate());
			spectacleDtoResponse.setAddress(savedSpectacle.getAddress());
			spectacleDtoResponse.setCategory(savedSpectacle.getSpectacleCategory());
			spectacleDtoResponse.setUserId(savedSpectacle.getForsaleUserId().getId());
			spectacleDtoResponse.setEmail(savedSpectacle.getForsaleUserId().getEmail());
			spectacleDtoResponse.setFirstName(savedSpectacle.getForsaleUserId().getFirstname());
			spectacleDtoResponse.setLastName(savedSpectacle.getForsaleUserId().getLastname());
			if(savedSpectacle.getPurshaseUserId() != null) {
				spectacleDtoResponse.setPurchaseUserEmail(spectacleTicket.getPurshaseUserId().getEmail());
			}
			return spectacleDtoResponse;
		} else {
			return null;
		}
	}

	public void deleteSpectacleTicketById(Long id) {
		spectacleRepository.deleteById(id);

	}
}
