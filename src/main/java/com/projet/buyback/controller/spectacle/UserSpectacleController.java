package com.projet.buyback.controller.spectacle;

import com.projet.buyback.model.User;
import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.schema.response.spectacle.SpectacleResponse;
import com.projet.buyback.schema.response.sport.SportResponse;
import com.projet.buyback.schema.response.user.UserPublicResponse;
import com.projet.buyback.service.user.UserService;
import com.projet.buyback.utils.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${api.baseURL}/user/tickets/spectacle")
public class UserSpectacleController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private SpectacleRepository spectacleRepository;

    @GetMapping("/purchased")
    public ResponseEntity<?> getPurchasedTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        try {
            String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
            if (userRepository.findByEmail(email).isPresent()) {
                User user = userRepository.findByEmail(email).get();

                List<SpectacleResponse> spectacleResponses = new ArrayList<>();
                for (Spectacle spectacle: spectacleRepository.findByPurchaser(user)) {
                    SpectacleResponse spectacleResponse = new SpectacleResponse(
                        spectacle.getId(),
                        spectacle.getName(),
                        spectacle.getPrice(),
                        spectacle.getStartDate(),
                        spectacle.getEndDate(),
                        spectacle.getAddress(),
                        spectacle.getSpectacleCategory(),
                        new UserPublicResponse(
                            spectacle.getSeller().getId(),
                            spectacle.getSeller().getFirstname(),
                            spectacle.getSeller().getLastname(),
                            spectacle.getSeller().getEmail()
                        ),
                        new UserPublicResponse(
                            spectacle.getPurchaser().getId(),
                            spectacle.getPurchaser().getFirstname(),
                            spectacle.getPurchaser().getLastname(),
                            spectacle.getPurchaser().getEmail()
                        )
                    );
                    spectacleResponses.add(spectacleResponse);
                }

                return ResponseEntity.ok(
                    spectacleResponses
                );
            }
        }
        catch(Exception e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Error: Could not recuperate tickets!"));
        }
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new MessageResponse("Error: Could not recuperate tickets!"));
    }

    @GetMapping("/for-sale")
    public ResponseEntity<?> getForSaleTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        return ResponseEntity.ok(new MessageResponse("Password changed!"));
    }

    @GetMapping("/sold")
    public ResponseEntity<?> getSoldTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        return ResponseEntity.ok(new MessageResponse("Password changed!"));
    }
}
