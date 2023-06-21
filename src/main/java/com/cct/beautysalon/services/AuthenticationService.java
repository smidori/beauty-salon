package com.cct.beautysalon.services;

import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.models.jwt.JwtAuthenticationResponse;
import com.cct.beautysalon.models.jwt.SignUpRequest;
import com.cct.beautysalon.models.jwt.SigninRequest;
import com.cct.beautysalon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        System.out.println("---------------------------> Signup request");
        var user = User.builder()
                .firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail())
                .login(request.getLogin()).password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SigninRequest request) {
        System.out.println("-----> login: " + request.getLogin() + " password: " + request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        var user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
