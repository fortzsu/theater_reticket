package com.reticket.reticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {


    @PostMapping("/register")
    public ResponseEntity<Void> register() {
        return ResponseEntity.ok().build();
    }
}
