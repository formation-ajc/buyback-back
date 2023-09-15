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
import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.model.spectacle.SpectacleCategory;
import com.projet.buyback.model.User;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.request.jwt.SpectacleDtoResponse;
import com.projet.buyback.schema.request.jwt.SpectacleRequest;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.service.ticket.SpectacleCategoryService;
import com.projet.buyback.service.ticket.SpectacleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.baseURL}")
public class SpectacleController {

	@Autowired
	private SpectacleService spectacleService;
	@Autowired
	private SpectacleCategoryService spectacleCategoryService;
	@Autowired
	private SpectacleRepository spectacleRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/spectacles")
	public ResponseEntity<?> getAllSpectacleTickets() {
		List<SpectacleDtoResponse> spectacleTickets = spectacleService.getAllSpectacleTickets();
		if (spectacleTickets != null) {
			return ResponseEntity.status(HttpStatus.OK).body(spectacleTickets);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Result is empty !"));
		}
	}

	@GetMapping("/spectacles/{id}")
	public ResponseEntity<?> getSpectacleTicketById(@PathVariable("id") Long spectacleId) {
		SpectacleDtoResponse spectacleDtoResponse = spectacleService.getSpectacleTicketById(spectacleId);
		if (spectacleDtoResponse != null) {
			return ResponseEntity.status(HttpStatus.OK).body(spectacleDtoResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}

	@PostMapping("/spectacles")
	public ResponseEntity<?> createSpectacleTicket(@RequestBody SpectacleRequest spectacleReq) {

		try {
			Spectacle newSpectacleTicket = new Spectacle();
			if (spectacleReq.getName() != null) {
				newSpectacleTicket.setName(spectacleReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}
			if (spectacleReq.getPrice() != null) {
				newSpectacleTicket.setPrice(spectacleReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}
			if (spectacleReq.getStartDate() != null) {
				newSpectacleTicket.setStartDate(spectacleReq.getStartDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date cannot be empty !"));
			}
			if (spectacleReq.getEndDate() != null) {
				newSpectacleTicket.setEndDate(spectacleReq.getEndDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The end date cannot be empty !"));
			}
			Address address = new Address();
			if (spectacleReq.getAddressName() != null) {
				address.setName(spectacleReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}
			if (spectacleReq.getAddressZipcode() != null) {
				address.setName(spectacleReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode cannot be empty !"));
			}
			newSpectacleTicket.setAddress(address);
			if (spectacleReq.getSpectaclecategoryId() != null) {
				Optional<SpectacleCategory> optSpectacleCategory = spectacleCategoryService
						.getSpectacleCategoryById(spectacleReq.getSpectaclecategoryId());
				if (optSpectacleCategory.isPresent()) {
					SpectacleCategory spectacleCategory = optSpectacleCategory.get();
					newSpectacleTicket.setSpectacleCategory(spectacleCategory);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new MessageResponse("Category not found !"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The category cannot be empty !"));
			}
			if (spectacleReq.getUserEmail() != null) {
				Optional<User> optUser = userRepository.findByEmail(spectacleReq.getUserEmail());
				if (optUser.isPresent()) {
					User user = optUser.get();
					newSpectacleTicket.setUser(user);
				}
			}
			spectacleService.createSpectacleTicket(newSpectacleTicket);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new MessageResponse("Ticket registered successfully !"));
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Problem encountred during creation !"));
		}

	}

	@PutMapping("/spectacles/{id}")
	public ResponseEntity<?> updateSpectacleTicket(@PathVariable("id") Long idSpectacleTicket,
			@RequestBody SpectacleRequest spectacleReq) {
		try {
			Optional<Spectacle> spectacle = spectacleRepository.findById(idSpectacleTicket);
			if (spectacle.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
			}
			Spectacle updatedSpectacleTicket = spectacle.get();
			if (spectacleReq.getName() != null) {
				updatedSpectacleTicket.setName(spectacleReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}
			if (spectacleReq.getPrice() != null) {
				updatedSpectacleTicket.setPrice(spectacleReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}
			if (spectacleReq.getStartDate() != null) {
				updatedSpectacleTicket.setStartDate(spectacleReq.getStartDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date cannot be empty !"));
			}
			if (spectacleReq.getEndDate() != null) {
				updatedSpectacleTicket.setEndDate(spectacleReq.getEndDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The end date cannot be empty !"));
			}
			Address address = new Address();
			if (spectacleReq.getAddressName() != null) {
				address.setName(spectacleReq.getAddressName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}
			if (spectacleReq.getAddressZipcode() != null) {
				address.setZipcode(spectacleReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode name cannot be empty !"));
			}
			updatedSpectacleTicket.setAddress(address);
			if (spectacleReq.getSpectaclecategoryId() != null) {
				Optional<SpectacleCategory> optSpectacleCategory = spectacleCategoryService
						.getSpectacleCategoryById(spectacleReq.getSpectaclecategoryId());
				if (optSpectacleCategory.isPresent()) {
					SpectacleCategory spectacleCategory = optSpectacleCategory.get();
					updatedSpectacleTicket.setSpectacleCategory(spectacleCategory);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new MessageResponse("Category not found !"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The category cannot be empty !"));
			}
			if (spectacleReq.getUserEmail() != null) {
				Optional<User> optUser = userRepository.findByEmail(spectacleReq.getUserEmail());
				if (optUser.isPresent()) {
					User user = optUser.get();
					updatedSpectacleTicket.setUser(user);
				}
			}
			spectacleService.createSpectacleTicket(updatedSpectacleTicket);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(spectacleService.createSpectacleTicket(updatedSpectacleTicket));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(new MessageResponse("Problem encoured during updating !"));
		}

	}

	@DeleteMapping("/spectacles/{id}")
	public ResponseEntity<?> deleteSpectacleTicketById(@PathVariable("id") Long idSpectacleTicket) {
		Optional<Spectacle> spectacle = spectacleRepository.findById(idSpectacleTicket);
		if (spectacle.isPresent()) {
			spectacleService.deleteSpectacleTicketById(idSpectacleTicket);
			return ResponseEntity.ok(new MessageResponse("Ticket deleted successfully !"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}
}
