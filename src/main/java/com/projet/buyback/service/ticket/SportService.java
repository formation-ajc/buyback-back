package com.projet.buyback.service.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.repository.sport.SportRepository;
import com.projet.buyback.schema.request.jwt.SportDtoResponse;

@Service
public class SportService {

	@Autowired
	private SportRepository sportRepository;

	public List<SportDtoResponse> getAllSportTickets() {
		List<Sport> sportTickets = sportRepository.findAll();
		if (!sportTickets.isEmpty()) {
			List<SportDtoResponse> sportTicketsDto = new ArrayList<>();
			for (Sport sport : sportTickets) {
				sportTicketsDto.add(new SportDtoResponse(sport.getId(), sport.getName(), sport.getPrice(),
						sport.getStartDate(), sport.getEndDate(), sport.getAddress(), sport.getSportCategory(),
						sport.getUser().getId(), sport.getUser().getEmail(), sport.getUser().getFirstname(), sport.getUser().getLastname()));
			}
			return sportTicketsDto;
		} else {
			return null;
		}
	}

	public SportDtoResponse getSportTicketById(Long id) {
		Optional<Sport> optSportTicket = sportRepository.findById(id);
		SportDtoResponse sportDtoresponse = new SportDtoResponse();
		if (optSportTicket.isPresent()) {
			Sport sportTicket = optSportTicket.get();
			sportDtoresponse.setId(sportTicket.getId());
			sportDtoresponse.setName(sportTicket.getName());
			sportDtoresponse.setPrice(sportTicket.getPrice());
			sportDtoresponse.setStartDate(sportTicket.getStartDate());
			sportDtoresponse.setEndDate(sportTicket.getEndDate());
			sportDtoresponse.setAddress(sportTicket.getAddress());
			sportDtoresponse.setCategory(sportTicket.getSportCategory());
			sportDtoresponse.setUserId(sportTicket.getUser().getId());
			sportDtoresponse.setEmail(sportTicket.getUser().getEmail());
			sportDtoresponse.setFirstName(sportTicket.getUser().getFirstname());
			sportDtoresponse.setLastName(sportTicket.getUser().getLastname());
			return sportDtoresponse;
		} else {
			return null;
		}
	}

	public SportDtoResponse createSportTicket(Sport sportTicket) {
		Sport savedSport = sportRepository.save(sportTicket);
		if(savedSport != null) {			
			SportDtoResponse sportDtoResponse = new SportDtoResponse();
			sportDtoResponse.setId(savedSport.getId());
			sportDtoResponse.setName(savedSport.getName());
			sportDtoResponse.setPrice(savedSport.getPrice());
			sportDtoResponse.setStartDate(savedSport.getStartDate());
			sportDtoResponse.setEndDate(savedSport.getEndDate());
			sportDtoResponse.setAddress(savedSport.getAddress());
			sportDtoResponse.setCategory(savedSport.getSportCategory());
			sportDtoResponse.setUserId(savedSport.getUser().getId());
			sportDtoResponse.setEmail(savedSport.getUser().getEmail());
			sportDtoResponse.setFirstName(savedSport.getUser().getFirstname());
			sportDtoResponse.setLastName(savedSport.getUser().getLastname());
			return sportDtoResponse;
		}else {
			return null;
		}
	}

	public void deleteSportTicket(Long id) {
		sportRepository.deleteById(id);
	}
}
