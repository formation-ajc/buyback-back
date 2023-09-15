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
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.model.sport.SportCategory;
import com.projet.buyback.model.User;
import com.projet.buyback.repository.sport.SportRepository;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.request.jwt.SportDtoResponse;
import com.projet.buyback.schema.request.jwt.SportRequest;
import com.projet.buyback.schema.response.jwt.MessageResponse;
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
	public ResponseEntity<?> getAllSportTickets() {
		List<SportDtoResponse> sportTickets = sportService.getAllSportTickets();
		if (sportTickets != null) {
			return ResponseEntity.status(HttpStatus.OK).body(sportTickets);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Result is empty !"));
		}
	}

	@GetMapping("/sports/{id}")
	public ResponseEntity<?> getSportTicketById(@PathVariable("id") Long sportId) {
		SportDtoResponse sportDtoResponse = sportService.getSportTicketById(sportId);
		if (sportDtoResponse != null) {
			return ResponseEntity.status(HttpStatus.OK).body(sportDtoResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}

	@PostMapping("/sports")
	public ResponseEntity<?> createSportTicket(@RequestBody SportRequest sportReq) {
		try {
			Sport newSportTicket = new Sport();
			if (sportReq.getName() != null && !sportReq.getName().isEmpty()) {
				newSportTicket.setName(sportReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}
			if (sportReq.getPrice() != null) {
				newSportTicket.setPrice(sportReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}
			if (sportReq.getStartDate() != null) {
				newSportTicket.setStartDate(sportReq.getStartDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date cannot be empty !"));
			}
			if (sportReq.getEndDate() != null) {
				newSportTicket.setEndDate(sportReq.getEndDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The end date cannot be empty !"));
			}
			Address address = new Address();
			if (sportReq.getAddressName() != null && !sportReq.getAddressName().isEmpty()) {
				address.setName(sportReq.getAddressName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}
			if (sportReq.getAddressZipcode() != null && !sportReq.getAddressZipcode().isEmpty()) {
				address.setZipcode(sportReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode cannot be empty !"));
			}
			newSportTicket.setAddress(address);
			
			System.out.println();
			if (sportReq.getSportcategoryId() != null) {
				Optional<SportCategory> optSportCategory = sportCategoryService
						.getSportCategoryById(sportReq.getSportcategoryId());
				if (optSportCategory.isPresent()) {
					SportCategory sportCategory = optSportCategory.get();
					newSportTicket.setSportCategory(sportCategory);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new MessageResponse("Category not found !"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The category cannot be empty !")); 
			}
			if (sportReq.getUserEmail() != null) {
				Optional<User> optUser = userRepository.findByEmail(sportReq.getUserEmail());
				if (optUser.isPresent()) {
					User user = optUser.get();
					newSportTicket.setForsaleUserId(user);
				}
			}
			sportService.createSportTicket(newSportTicket);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new MessageResponse("Ticket registered successfully !"));

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Problem encountred during creation !"));
		}

	}

	@PutMapping("/sports/{id}")
	public ResponseEntity<?> updateSportTicket(@PathVariable("id") Long idSportTicket,
			@RequestBody SportRequest sportReq) {

		try {
			Optional<Sport> sport = sportRepository.findById(idSportTicket);
			if (sport.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
			}
			Sport updatedSportTicket = sport.get();
			if (sportReq.getName() != null && !sportReq.getName().isEmpty()) {
				updatedSportTicket.setName(sportReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}
			if (sportReq.getPrice() != null) {
				updatedSportTicket.setPrice(sportReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}
			if (sportReq.getStartDate() != null) {
				updatedSportTicket.setStartDate(sportReq.getStartDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date cannot be empty !"));
			}
			if (sportReq.getEndDate() != null) {
				updatedSportTicket.setEndDate(sportReq.getEndDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The end date cannot be empty !"));
			}
			Address address = new Address();
			if (sportReq.getAddressName() != null) {
				address.setName(sportReq.getAddressName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}
			if (sportReq.getAddressZipcode() != null && !sportReq.getAddressName().isEmpty()) {
				address.setZipcode(sportReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode cannot be empty !"));
			}
			updatedSportTicket.setAddress(address);
			if (sportReq.getSportcategoryId() != null && !sportReq.getAddressZipcode().isEmpty()) {
				Optional<SportCategory> optSportCategory = sportCategoryService
						.getSportCategoryById(sportReq.getSportcategoryId());
				if (optSportCategory.isPresent()) {
					SportCategory sportCategory = optSportCategory.get();
					updatedSportTicket.setSportCategory(sportCategory);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new MessageResponse("Category not found !"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("Category cannot be empty !"));
			}

			if (sportReq.getUserEmail() != null) {
				Optional<User> optUser = userRepository.findByEmail(sportReq.getUserEmail());
				if (optUser.isPresent()) {
					User user = optUser.get();
					updatedSportTicket.setForsaleUserId(user);
				}
			}
			sportService.createSportTicket(updatedSportTicket);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Ticket updated successfully !"));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(new MessageResponse("Problem encoured during updating !"));
		}

	}

	@DeleteMapping("/sports/{id}")
	public ResponseEntity<?> deleteSportTicketById(@PathVariable("id") Long idSportTicket) {
		Optional<Sport> sport = sportRepository.findById(idSportTicket);
		if (sport.isPresent()) {
			sportService.deleteSportTicket(idSportTicket);
			return ResponseEntity.ok(new MessageResponse("Ticket deleted successfully !"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}
}
