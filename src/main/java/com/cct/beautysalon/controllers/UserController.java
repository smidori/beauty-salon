package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.UserDTO;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;

    //convert the entity to DTO
    private UserDTO toDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    //convert the DTO to entity
    private User toEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }

    /**
     * Create a new user
     * @param userDTO
     * @return
     */
    @PostMapping
    public UserDTO save(@Valid @RequestBody UserDTO userDTO) {
        User user = toEntity(userDTO);
        User userSaved = userService.save(user);
        return toDTO(userSaved);
    }

    /**
     * Get user by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return toDTO(userService.findUserById(id));
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
        User user = toEntity(userDTO);
        userService.update(id, user);
    }

    /**
     * Delete a user
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
