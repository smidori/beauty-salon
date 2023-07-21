package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.UserSummaryDTO;
import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.exceptions.BadCredentialsException;
import com.cct.beautysalon.exceptions.UsernameRegisteredException;
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

    public JwtAuthenticationResponse register(SignUpRequest request) {
        try{
            var userRegistered = userRepository.findByLogin(request.getLogin());
            if(userRegistered.isPresent()){
                throw new UsernameRegisteredException();
            }
            var user = User.builder()
                    .firstName(request.getFirstName()).lastName(request.getLastName())
                    .email(request.getEmail())
                    .login(request.getLogin())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .gender(request.getGender())
                    .role(Role.CLIENT).build();
            userRepository.save(user);
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        }catch (UsernameRegisteredException e){
            throw new UsernameRegisteredException(e.getMessage());
        }


    }

    public JwtAuthenticationResponse login(SigninRequest request) {
        System.out.println("-----> login: " + request.getLogin() + " password: " + request.getPassword());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

            var user = userRepository.findByLogin(request.getLogin())
                    .orElseThrow(() -> new BadCredentialsException("Invalid login or password"));

            var jwt = jwtService.generateToken(user);

            UserSummaryDTO userSummaryDTO = UserSummaryDTO.builder().id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .login(user.getLogin())
                    .build();

            return JwtAuthenticationResponse.builder()
                    .token(jwt)
                    .userDetails(userSummaryDTO)
                    .build();
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new BadCredentialsException("Invalid login or password",e);

        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new com.cct.beautysalon.exceptions.AuthenticationException("Authentication failed",e);
        }
    }


}
