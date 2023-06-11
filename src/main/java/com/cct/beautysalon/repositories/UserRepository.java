package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
