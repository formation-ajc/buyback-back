package com.projet.buyback.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
//    @Secured("USER")
    public String userAccess() {
        return "User Here.";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
//    @Secured("ADMIN")
    public String adminAccess() {
        return "Admin Here.";
    }
}
