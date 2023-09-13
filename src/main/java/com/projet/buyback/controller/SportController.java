package com.projet.buyback.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projet.buyback.model.Address;
import com.projet.buyback.model.Sport;
import com.projet.buyback.model.SportCategory;
import com.projet.buyback.model.User;
import com.projet.buyback.repository.SportRepository;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.request.jwt.SportDtoResponse;
import com.projet.buyback.schema.request.jwt.SportRequest;
import com.projet.buyback.service.ticket.SportCategoryService;
import com.projet.buyback.service.ticket.SportService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.baseURL}")
public class SportController {

	@Autowired
	private SportService sportService;
	@Autowired
	private SportCategoryService sportCategoryService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SportRepository sportRepository;

	@GetMapping("/sports")
	public ResponseEntity<List<SportDtoResponse>> getAllSportTickets() {
		List<SportDtoResponse> sportTickets = sportService.getAllSportTickets();
		if (sportTickets == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(sportTickets);
		}
	}

	@GetMapping("/sports/{id}")
	public ResponseEntity<SportDtoResponse> getSportTicketById(@PathVariable("id") Long sportId) {
		SportDtoResponse sportDtoResponse = sportService.getSportTicketById(sportId);
		if (sportDtoResponse != null) {
			return ResponseEntity.status(HttpStatus.OK).body(sportDtoResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
		}
	}

	@PostMapping("/sports")
	public ResponseEntity<SportDtoResponse> createSportTicket(@RequestBody SportRequest sportReq) {

		try {
			Sport newSportTicket = new Sport();
			if (sportReq.getName() != null) {
				newSportTicket.setName(sportReq.getName());
			}
			if (sportReq.getPrice() != null) {
				newSportTicket.setPrice(sportReq.getPrice());
			}
			if (sportReq.getStartDate() != null) {
				newSportTicket.setStartDate(sportReq.getStartDate());
			}
			if (sportReq.getEndDate() != null) {
				newSportTicket.setEndDate(sportReq.getEndDate());
			}

			if (sportReq.getAddressName() != null && sportReq.getAddressZipcode() != null) {
				Address address = new Address(sportReq.getAddressName(), sportReq.getAddressZipcode());
				newSportTicket.setAddress(address);
			}
			if (sportReq.getSportcategoryId() != null) {
				Optional<SportCategory> optSportCategory = sportCategoryService
						.getSportCategoryById(sportReq.getSportcategoryId());
				if (optSportCategory.isPresent()) {
					SportCategory sportCategory = optSportCategory.get();
					newSportTicket.setSportCategory(sportCategory);
				}
			}
			if (sportReq.getUserId() != null) {
				Optional<User> optUser = userRepository.findById(sportReq.getUserId());
				if (optUser.isPresent()) {
					User user = optUser.get();
					newSportTicket.setUser(user);
				}
			}
			sportService.createSportTicket(newSportTicket);
			return ResponseEntity.status(HttpStatus.CREATED).body(sportService.createSportTicket(newSportTicket));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}

	@PutMapping("/sports/{id}")
	public ResponseEntity<SportDtoResponse> updateSportTicket(@PathVariable("id") Long idSportTicket,
			@RequestBody SportRequest sportReq) {

		Optional<Sport> sport = sportRepository.findById(idSportTicket);
		if (sport.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Sport updatedSportTicket = sport.get();
		if (sportReq.getName() != null) {
			updatedSportTicket.setName(sportReq.getName());
		}
		if (sportReq.getPrice() != null) {
			updatedSportTicket.setPrice(sportReq.getPrice());
		}
		if (sportReq.getStartDate() != null) {
			updatedSportTicket.setStartDate(sportReq.getStartDate());
		}
		if (sportReq.getEndDate() != null) {
			updatedSportTicket.setEndDate(sportReq.getEndDate());
		}
		if (sportReq.getAddressName() != null && sportReq.getAddressZipcode() != null) {
			updatedSportTicket.setAddress(new Address(sportReq.getAddressName(), sportReq.getAddressZipcode()));
		}
		Optional<SportCategory> optSportCategory = sportCategoryService
				.getSportCategoryById(sportReq.getSportcategoryId());
		if (optSportCategory.isPresent()) {
			SportCategory sportCategory = optSportCategory.get();
			updatedSportTicket.setSportCategory(sportCategory);
		}
		if (sportReq.getUserId() != null) {
			Optional<User> optUser = userRepository.findById(sportReq.getUserId());
			if (optUser.isPresent()) {
				User user = optUser.get();
				updatedSportTicket.setUser(user);
			}
		}
		sportService.createSportTicket(updatedSportTicket);
		return ResponseEntity.status(HttpStatus.OK).body(sportService.createSportTicket(updatedSportTicket));
	}

	@DeleteMapping("/sports/{id}")
	public ResponseEntity<HttpStatus> deleteSportTicketById(@PathVariable("id") Long idSportTicket) {
		Optional<Sport> sport = sportRepository.findById(idSportTicket);
		if (sport.isPresent()) {
			sportService.deleteSportTicket(idSportTicket);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
