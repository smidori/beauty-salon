package com.cct.beautysalon.controllers;
import com.cct.beautysalon.models.jwt.JwtAuthenticationResponse;
import com.cct.beautysalon.models.jwt.SignUpRequest;
import com.cct.beautysalon.models.jwt.SigninRequest;
import com.cct.beautysalon.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody SignUpRequest request) {
        System.out.println("request -----------> " + request);
        var test = ResponseEntity.ok(authenticationService.register(request));
        System.out.println("auth signup -----------> " + test);
        return test;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SigninRequest request) {
        System.out.println("request -----------> " + request);
        var test = ResponseEntity.ok(authenticationService.login(request));
        System.out.println("auth signin -----------> " + test);
        return test;
    }
}