package com.example.demo.controller;

import com.example.demo.payload.AuthenticationResponse;
import com.example.demo.security.dtos.LoginDTO;
import com.example.demo.security.dtos.RegisterDTO;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/auth/")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService service;


    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginDTO request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterDTO request) {
        System.out.println("register  " + request);
        return ResponseEntity.ok(service.register(request));
    }
}
