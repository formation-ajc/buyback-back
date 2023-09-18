package com.projet.buyback.service.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.buyback.model.User;
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
				String purshaseUserEmail = null;
				if (sport.getPurshaseUserId() != null) {
					purshaseUserEmail = sport.getForsaleUserId().getEmail();
				}
				sportTicketsDto.add(new SportDtoResponse(sport.getId(), sport.getName(), sport.getPrice(),
						sport.getStartDate(), sport.getEndDate(), sport.getAddress(), sport.getSportCategory(),
						sport.getForsaleUserId().getId(), sport.getForsaleUserId().getEmail(),
						sport.getForsaleUserId().getFirstname(), sport.getForsaleUserId().getLastname(),
						purshaseUserEmail));
			}
			return sportTicketsDto;
		} else {
			return null;
		}
	}
	
	public List<SportDtoResponse> getAllSportTicketsByForsaleUser(User user) {
		List<Sport> sportTicketsByForsaleUser = sportRepository.findByForsaleUserId(user);
		if (!sportTicketsByForsaleUser.isEmpty()) {
			List<SportDtoResponse> sportTicketsDto = new ArrayList<>();
			for (Sport sport : sportTicketsByForsaleUser) {
				String purshaseUserEmail = null;
				if (sport.getPurshaseUserId() != null) {
					purshaseUserEmail = sport.getForsaleUserId().getEmail();
				}
				sportTicketsDto.add(new SportDtoResponse(sport.getId(), sport.getName(), sport.getPrice(),
						sport.getStartDate(), sport.getEndDate(), sport.getAddress(), sport.getSportCategory(),
						sport.getForsaleUserId().getId(), sport.getForsaleUserId().getEmail(),
						sport.getForsaleUserId().getFirstname(), sport.getForsaleUserId().getLastname(),
						purshaseUserEmail));
			}
			return sportTicketsDto;
		} else {
			return null;
		}
	}

	public SportDtoResponse getSportTicketById(Long id) {
		Optional<Sport> optSportTicket = sportRepository.findById(id);
		SportDtoResponse sportDtoResponse = new SportDtoResponse();
		if (optSportTicket.isPresent()) {
			Sport sportTicket = optSportTicket.get();
			sportDtoResponse.setId(sportTicket.getId());
			sportDtoResponse.setName(sportTicket.getName());
			sportDtoResponse.setPrice(sportTicket.getPrice());
			sportDtoResponse.setStartDate(sportTicket.getStartDate());
			sportDtoResponse.setEndDate(sportTicket.getEndDate());
			sportDtoResponse.setAddress(sportTicket.getAddress());
			sportDtoResponse.setCategory(sportTicket.getSportCategory());
			sportDtoResponse.setUserId(sportTicket.getForsaleUserId().getId());
			sportDtoResponse.setEmail(sportTicket.getForsaleUserId().getEmail());
			sportDtoResponse.setFirstName(sportTicket.getForsaleUserId().getFirstname());
			sportDtoResponse.setLastName(sportTicket.getForsaleUserId().getLastname());
			if (sportTicket.getPurshaseUserId() != null) {
				sportDtoResponse.setPurchaseUserEmail(sportTicket.getPurshaseUserId().getEmail());
			}
			return sportDtoResponse;
		} else {
			return null;
		}
	}

	public SportDtoResponse createSportTicket(Sport sportTicket) {
		Sport savedSport = sportRepository.save(sportTicket);
		if (savedSport != null) {
			SportDtoResponse sportDtoResponse = new SportDtoResponse();
			sportDtoResponse.setId(savedSport.getId());
			sportDtoResponse.setName(savedSport.getName());
			sportDtoResponse.setPrice(savedSport.getPrice());
			sportDtoResponse.setStartDate(savedSport.getStartDate());
			sportDtoResponse.setEndDate(savedSport.getEndDate());
			sportDtoResponse.setAddress(savedSport.getAddress());
			sportDtoResponse.setCategory(savedSport.getSportCategory());
			sportDtoResponse.setUserId(savedSport.getForsaleUserId().getId());
			sportDtoResponse.setEmail(savedSport.getForsaleUserId().getEmail());
			sportDtoResponse.setFirstName(savedSport.getForsaleUserId().getFirstname());
			sportDtoResponse.setLastName(savedSport.getForsaleUserId().getLastname());
			if (savedSport.getPurshaseUserId() != null) {
				sportDtoResponse.setPurchaseUserEmail(savedSport.getPurshaseUserId().getEmail());
			}
			return sportDtoResponse;
		} else {
			return null;
		}
	}

	public void deleteSportTicket(Long id) {
		sportRepository.deleteById(id);
	}
}
