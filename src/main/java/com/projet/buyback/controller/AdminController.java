package com.projet.buyback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.buyback.model.User;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.response.security.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${api.baseURL}/users")
public class AdminController {
	@Autowired
	private UserRepository userRepository;
	
    @GetMapping("")
    public ResponseEntity<?> getAllUsers(){
    	List<User> users = userRepository.findAll();
    	if(!users.isEmpty()) {
    		return ResponseEntity.ok(users);
    	}
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("No result!"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
    	User user = userRepository.findById(id).get();
    	if(user != null) {
    		userRepository.delete(user);
    		return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    	}
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found!"));

    }
}
