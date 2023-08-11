package com.cct.beautysalon.services;

import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.exceptions.EmailAlreadyRegisteredException;
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
public class UserService {

    private final UserRepository userRepository;

    /**
     * find all
     *
     * @return
     */
    public Iterable<User> findAll() {
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("lastName").ascending());
        return userRepository.findAll(sort);
    }

    /**
     * find User By Id
     *
     * @param id
     * @return
     */
    public User findUserById(Long id) {
        return findOrThrowUserById(id);
    }

    /**
     * find User By Role
     *
     * @param role
     * @return
     */
    public List<User> findUserByRole(Role role) {
        return userRepository.findUserByRole(role);
    }

    /**
     * save
     *
     * @param user
     * @return
     */
    public User save(User user) {
        var userRegistered = userRepository.findByLogin(user.getLogin());
        if (userRegistered.isPresent()) {
            throw new UsernameRegisteredException();
        }
        var userEmailRegistered = userRepository.findByEmail(user.getEmail());
        if (userEmailRegistered.isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }

        return userRepository.save(user);
    }

    /**
     * delete
     *
     * @param id
     */
    public void delete(Long id) {
        findOrThrowUserById(id);
        userRepository.deleteById(id);
    }

    /**
     * update
     *
     * @param id
     * @param user
     */
    public void update(Long id, User user) {
        findOrThrowUserById(id);
        userRepository.save(user);
    }

    /**
     * find Or Throw User By Id
     *
     * @param id
     * @return
     */
    private User findOrThrowUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User by id: " + id + "was not found"));
    }

    /**
     * find By Login
     *
     * @param login
     * @return
     */
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * find By Email
     *
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found"));
    }

    /**
     * userDetailsService => FOR LOGIN AND AUTHENTICATION
     *
     * @return
     */
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
