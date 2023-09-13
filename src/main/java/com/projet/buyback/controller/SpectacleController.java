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
import com.projet.buyback.model.Spectacle;
import com.projet.buyback.model.SpectacleCategory;
import com.projet.buyback.model.User;
import com.projet.buyback.repository.SpectacleRepository;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.request.jwt.SpectacleDtoResponse;
import com.projet.buyback.schema.request.jwt.SpectacleRequest;
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
	public ResponseEntity<List<SpectacleDtoResponse>> getAllSpectacleTickets() {
		List<SpectacleDtoResponse> spectacleTickets = spectacleService.getAllSpectacleTickets();
		if (spectacleTickets == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(spectacleTickets);
		}
	}

	@GetMapping("/spectacles/{id}")
	public ResponseEntity<SpectacleDtoResponse> getSpectacleTicketById(@PathVariable("id") Long spectacleId) {
		SpectacleDtoResponse spectacleDtoResponse = spectacleService.getSpectacleTicketById(spectacleId);
		if (spectacleDtoResponse != null) {
			return ResponseEntity.status(HttpStatus.OK).body(spectacleDtoResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/spectacles")
	public ResponseEntity<SpectacleDtoResponse> createSpectacleTicket(@RequestBody SpectacleRequest spectacleReq) {

		try {
			Spectacle newSpectacleTicket = new Spectacle();
			if (spectacleReq.getName() != null) {
				newSpectacleTicket.setName(spectacleReq.getName());
			}
			if (spectacleReq.getPrice() != null) {
				newSpectacleTicket.setPrice(spectacleReq.getPrice());
			}
			if (spectacleReq.getStartDate() != null) {
				newSpectacleTicket.setStartDate(spectacleReq.getStartDate());
			}
			if (spectacleReq.getEndDate() != null) {
				newSpectacleTicket.setEndDate(spectacleReq.getEndDate());
			}

			if (spectacleReq.getAddressName() != null && spectacleReq.getAddressZipcode() != null) {
				Address address = new Address(spectacleReq.getAddressName(), spectacleReq.getAddressZipcode());
				newSpectacleTicket.setAddress(address);
			}
			if (spectacleReq.getSpectaclecategoryId() != null) {
				Optional<SpectacleCategory> optSpectacleCategory = spectacleCategoryService
						.getSpectacleCategoryById(spectacleReq.getSpectaclecategoryId());
				if (optSpectacleCategory.isPresent()) {
					SpectacleCategory spectacleCategory = optSpectacleCategory.get();
					newSpectacleTicket.setSpectacleCategory(spectacleCategory);
				}
			}
			if (spectacleReq.getUserId() != null) {
				Optional<User> optUser = userRepository.findById(spectacleReq.getUserId());
				if (optUser.isPresent()) {
					User user = optUser.get();
					newSpectacleTicket.setUser(user);
				}
			}
			spectacleService.createSpectacleTicket(newSpectacleTicket);
			return ResponseEntity.status(HttpStatus.CREATED).body(spectacleService.createSpectacleTicket(newSpectacleTicket));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

	}

	@PutMapping("/spectacles/{id}")
	public ResponseEntity<SpectacleDtoResponse> updateSpectacleTicket(@PathVariable("id") Long idSpectacleTicket,
			@RequestBody SpectacleRequest spectacleReq) {

		Optional<Spectacle> spectacle = spectacleRepository.findById(idSpectacleTicket);
		if (spectacle.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Spectacle updatedSpectacleTicket = spectacle.get();
		if (spectacleReq.getName() != null) {
			updatedSpectacleTicket.setName(spectacleReq.getName());
		}
		if (spectacleReq.getPrice() != null) {
			updatedSpectacleTicket.setPrice(spectacleReq.getPrice());
		}
		if (spectacleReq.getStartDate() != null) {
			updatedSpectacleTicket.setStartDate(spectacleReq.getStartDate());
		}
		if (spectacleReq.getEndDate() != null) {
			updatedSpectacleTicket.setEndDate(spectacleReq.getEndDate());
		}
		if (spectacleReq.getAddressName() != null && spectacleReq.getAddressZipcode() != null) {
			updatedSpectacleTicket
					.setAddress(new Address(spectacleReq.getAddressName(), spectacleReq.getAddressZipcode()));
		}
		Optional<SpectacleCategory> optSpectacleCategory = spectacleCategoryService
				.getSpectacleCategoryById(spectacleReq.getSpectaclecategoryId());
		if (optSpectacleCategory.isPresent()) {
			SpectacleCategory spectacleCategory = optSpectacleCategory.get();
			updatedSpectacleTicket.setSpectacleCategory(spectacleCategory);
		}
		if (spectacleReq.getUserId() != null) {
			Optional<User> optUser = userRepository.findById(spectacleReq.getUserId());
			if (optUser.isPresent()) {
				User user = optUser.get();
				updatedSpectacleTicket.setUser(user);
			}
		}
		spectacleService.createSpectacleTicket(updatedSpectacleTicket);
		return ResponseEntity.status(HttpStatus.OK).body(spectacleService.createSpectacleTicket(updatedSpectacleTicket));
	}

	@DeleteMapping("/spectacles/{id}")
	public ResponseEntity<HttpStatus> deleteSpectacleTicketById(@PathVariable("id") Long idSpectacleTicket) {
		Optional<Spectacle> spectacle = spectacleRepository.findById(idSpectacleTicket);
		if (spectacle.isPresent()) {
			spectacleService.deleteSpectacleTicketById(idSpectacleTicket);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
