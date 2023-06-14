package com.reticket.reticket.controller;

import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AppUserService appUserService;

    @Autowired
    public AuthenticationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMe(Principal principal) {
        return ResponseEntity.ok(principal.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserDetails(Principal principal) {
        return ResponseEntity.ok(principal.toString());
    }
}
