package com.cct.beautysalon.services;

import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.exceptions.UsernameRegisteredException;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor //por algumas razão, dá erro na criação do bean
public class UserService {

    private final UserRepository userRepository;
    public Iterable<User> findAll() {
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("lastName").ascending());
        return userRepository.findAll(sort);
    }

    public User findUserById(Long id) {
        return findOrThrowUserById(id);
    }

    public List<User> findUserByRole(Role role) {
        return userRepository.findUserByRole(role);
    }

    public User save(User user) {
        var userRegistered = userRepository.findByLogin(user.getLogin());
        if(userRegistered.isPresent()){
            throw new UsernameRegisteredException();
        }

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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found"));
    }

    //FOR LOGIN AND AUTHENTICATION
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByLogin(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

}
