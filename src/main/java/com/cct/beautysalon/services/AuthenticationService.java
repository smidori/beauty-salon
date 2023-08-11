package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.UserSummaryDTO;
import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.exceptions.BadCredentialsException;
import com.cct.beautysalon.exceptions.EmailAlreadyRegisteredException;
import com.cct.beautysalon.exceptions.InvalidUsernameException;
import com.cct.beautysalon.exceptions.UsernameRegisteredException;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.models.jwt.JwtAuthenticationResponse;
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
    private final UserService userService;

    /**
     * register client
     * @param user
     * @return
     */
    public JwtAuthenticationResponse register(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.CLIENT);

            userService.save(user);
            var jwt = jwtService.generateToken(user);
            UserSummaryDTO userSummaryDTO = UserSummaryDTO.builder().id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .login(user.getLogin())
                    .build();


            return JwtAuthenticationResponse.builder().token(jwt).userDetails(userSummaryDTO).build();
        } catch (UsernameRegisteredException e) {
            throw new UsernameRegisteredException(e.getMessage());
        } catch (EmailAlreadyRegisteredException e) {
            throw new EmailAlreadyRegisteredException(e.getMessage());
        }


    }

    /**
     * login
     * @param request
     * @return
     */
    public JwtAuthenticationResponse login(SigninRequest request) {
        try {
            var user = userRepository.findByLogin(request.getLogin())
                    .orElseThrow(() -> new InvalidUsernameException());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
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
            throw new BadCredentialsException("Invalid password", e);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new com.cct.beautysalon.exceptions.AuthenticationException("Authentication failed", e);
        } catch (InvalidUsernameException e) {
            throw new InvalidUsernameException();
        }
    }


}
