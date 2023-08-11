package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.UserDTO;
import com.cct.beautysalon.DTO.UserSummaryDTO;
import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.exceptions.CantBeDeletedException;
import com.cct.beautysalon.exceptions.EmailAlreadyRegisteredException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.exceptions.UsernameRegisteredException;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.models.jwt.JwtAuthenticationResponse;
import com.cct.beautysalon.services.UserService;
import com.cct.beautysalon.utils.ErrorResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@CrossOrigin(allowedHeaders = "Content-Type")
//@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ModelMapper mapper;

    //convert the entity to DTO
    private UserDTO toDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }


    //convert the entity to UserSummaryDTO
    private UserSummaryDTO toSummaryDTO(User user) {
        return mapper.map(user, UserSummaryDTO.class);
    }


    //convert the DTO to entity
    private User toEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }

    //convert the UserSummaryDTO to entity
    private User toEntity(UserSummaryDTO userSummaryDTO) {
        return mapper.map(userSummaryDTO, User.class);
    }


    @GetMapping
    public List<UserDTO> getUsers() {
        var users = StreamSupport.stream(userService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return users.stream().map(this::toDTO).toList();
    }

    /**
     * Create a new user
     * @param userDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody UserDTO userDTO) {
        try {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User user = toEntity(userDTO);
            User userSaved = userService.save(user);
            return ResponseEntity.ok(toDTO(userSaved));
        }catch (UsernameRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

    }

    /**
     * Get user by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        System.out.println("getUserById => " + id);
        return toDTO(userService.findUserById(id));
    }

    @GetMapping("/role/{role}")
    public List<UserSummaryDTO> getUserByRole(@PathVariable("role") Role role) {
        var users = StreamSupport.stream(userService.findUserByRole(role).spliterator(), false)
                .collect(Collectors.toList());
        return users.stream().map(this::toSummaryDTO).toList();
    }

    /**
     * Update user by id
     * @param id
     * @param userDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody UserDTO userDTO) {
        if(!id.equals(userDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id doesn't match");
        }
        var userSaved = userService.findUserById(id);
        //to not encode the encoded password
        if(!userSaved.getPassword().equals(userDTO.getPassword())) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User user = toEntity(userDTO);
        userService.update(id, user);
    }

    /**
     * delete user if there is no reference to
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try{
            userService.delete(id);
            return ResponseEntity.ok().build();
        }catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(new NotFoundException().getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(new CantBeDeletedException().getMessage()));
        }
    }

}
