package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@AllArgsConstructor //por algumas razão, dá erro na criação do bean
public class UserService {
    private final UserRepository userRepository; //initialized by Spring @AllArgsConstructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return findOrThrowUserById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        findOrThrowUserById(id);
        userRepository.deleteById(id);
    }

    public void update(Long id, User user) {
        findOrThrowUserById(id);
        userRepository.save(user);
    }

    private User findOrThrowUserById(Long id) {
        return userRepository.findById(id)
              .orElseThrow(
                      () -> new NotFoundException("User by id: " + id + "was not found"));
    }
}
