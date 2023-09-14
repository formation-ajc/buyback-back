package com.projet.buyback.controller;

import com.projet.buyback.model.User;
import com.projet.buyback.model.security.RefreshToken;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.request.security.LoginRequest;
import com.projet.buyback.schema.request.user.UpdatePasswordUserRequest;
import com.projet.buyback.schema.request.user.UpdateUserRequest;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.schema.response.security.SignupResponse;
import com.projet.buyback.service.security.RefreshTokenService;
import com.projet.buyback.service.security.UserDetailsImpl;
import com.projet.buyback.service.user.UserService;
import com.projet.buyback.utils.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${api.baseURL}/user")
public class UserController {

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    UserService userService;

    JwtUtils jwtUtils;

    RefreshTokenService refreshTokenService;

    public UserController(
        AuthenticationManager authenticationManager,
        UserRepository userRepository,
        UserService userService,
        JwtUtils jwtUtils,
        RefreshTokenService refreshTokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            // On récupère l'email du token qui est l'email avant modification
            String actualEmail = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(actualEmail, updateUserRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // On récupère l'id de l'utilisateur
            User actualUser = null;
            if (userRepository.findByEmail(actualEmail).isPresent()) {
                actualUser = userRepository.findByEmail(actualEmail).get();

                if (userRepository.findByEmail(updateUserRequest.getEmail()).isPresent()) {
                    if (!Objects.equals(actualUser.getId(), userRepository.findByEmail(updateUserRequest.getEmail()).get().getId())) {
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
                    }
                }

            }


            //On update l'utilisateur
            User updateUser = new User(
                updateUserRequest.getFirstname(),
                updateUserRequest.getLastname(),
                updateUserRequest.getEmail()
            );
            if (actualUser != null) {
                updateUser.setId(actualUser.getId());
                updateUser.setPassword(actualUser.getPassword());
            }
            User userUpdated = userService.update(updateUser);


            //On regénère les tokens avec le nouvel email
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // nouvel email
            userDetails.setEmail(userUpdated.getEmail());

            // nouveau access token
            String jwt = jwtUtils.generateJwtToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            // nouveau refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());


            return ResponseEntity.ok(new SignupResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getEmail(), userUpdated.getFirstname(), userUpdated.getLastname(), roles));
        }
        catch(Exception e) {// see note 2
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Error: User not exist!"));
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordUserRequest updatePasswordUserRequest) {
        try {

            return ResponseEntity.ok(new MessageResponse("User modify successfully!"));
        }
        catch(Exception e) {// see note 2
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Error: User not exist!"));
        }
    }
}
