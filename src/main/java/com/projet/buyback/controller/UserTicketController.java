package com.projet.buyback.controller;

import com.projet.buyback.model.User;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.repository.sport.SportRepository;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.schema.response.user.UserSportTicketResponse;
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
@RequestMapping("${api.baseURL}/user/tickets")
public class UserTicketController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private SportRepository sportRepository;

    @GetMapping("/sport/purchased")
    public ResponseEntity<?> getPurchasedTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        try {
            String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
            if (userRepository.findByEmail(email).isPresent()) {
                User user = userRepository.findByEmail(email).get();

                List<UserSportTicketResponse> sportTicketResponses = new ArrayList<>();
                for (Sport sportTicket: sportRepository.findBypurshaseUserId(user)) {
                    UserSportTicketResponse userSportTicketResponse = UserSportTicketResponse.getUserSportTicketResponse(sportTicket);
                    sportTicketResponses.add(userSportTicketResponse);
                }

                return ResponseEntity.ok(
                    sportTicketResponses
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

    @GetMapping("/sport/for-sale")
    public ResponseEntity<?> getForSaleTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        return ResponseEntity.ok(new MessageResponse("Password changed!"));
    }

    @GetMapping("/sport/sold")
    public ResponseEntity<?> getSoldTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        return ResponseEntity.ok(new MessageResponse("Password changed!"));
    }
}
