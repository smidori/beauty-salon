package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.UserDTO;
import com.cct.beautysalon.exceptions.AuthenticationException;
import com.cct.beautysalon.exceptions.BadCredentialsException;
import com.cct.beautysalon.exceptions.UsernameRegisteredException;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.models.jwt.JwtAuthenticationResponse;
import com.cct.beautysalon.models.jwt.SigninRequest;
import com.cct.beautysalon.services.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
    private final ModelMapper mapper;

    private User toEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }

    /**
     * This api is used to register the client
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody UserDTO userDTO) {

        try {
            var response = authenticationService.register(toEntity(userDTO));
            return ResponseEntity.ok(response);
        }catch (UsernameRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JwtAuthenticationResponse(null, null, e.getMessage()));
        }
    }

    /**
     * Endpoint for authenticating
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SigninRequest request) {
        try {
            var response = authenticationService.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtAuthenticationResponse(null, null, e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtAuthenticationResponse(null, null, e.getMessage()));
        }
    }
}