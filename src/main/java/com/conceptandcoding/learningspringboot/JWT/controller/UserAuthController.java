package com.conceptandcoding.learningspringboot.JWT.controller;

import com.conceptandcoding.learningspringboot.JWT.entity.UserAuthEntity;
import com.conceptandcoding.learningspringboot.JWT.service.UserAuthEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    UserAuthEntityService userAuthEntityService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserAuthEntity userAuthDetails) {
        // Hash the password before saving
        userAuthDetails.setPassword(passwordEncoder.encode(userAuthDetails.getPassword()));

        userAuthEntityService.save(userAuthDetails);

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/users")
    public String getUsersDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "fetched user details successfully";
    }
}
