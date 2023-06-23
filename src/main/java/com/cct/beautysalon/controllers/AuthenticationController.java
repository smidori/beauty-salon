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

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        System.out.println("request -----------> " + request);
        var test = ResponseEntity.ok(authenticationService.signup(request));
        System.out.println("auth signup -----------> " + test);
        return test;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        System.out.println("request -----------> " + request);
        var test = ResponseEntity.ok(authenticationService.signin(request));
        System.out.println("auth signin -----------> " + test);
        return test;
    }
}