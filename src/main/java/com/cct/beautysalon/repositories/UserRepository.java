package com.cct.beautysalon.repositories;

import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findUserByRole(Role role);
}
