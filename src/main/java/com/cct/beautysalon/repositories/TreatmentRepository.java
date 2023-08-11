package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.User;

import jakarta.persistence.OrderBy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment,Long>  {
    List<Treatment> findAll(Sort sort);

}
