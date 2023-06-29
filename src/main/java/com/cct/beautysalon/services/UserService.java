package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor //por algumas razão, dá erro na criação do bean
public class UserService {
    private final UserRepository userRepository;
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

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    //FOR LOGIN AND AUTHENTICATION
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                System.out.println("-------------------------------------->UserService loadUserByUsername " + username);
                var user = userRepository.findByLogin(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                System.out.println("-------------------------------------->UserService user " + user);
                return user;
            }
        };
    }

}
