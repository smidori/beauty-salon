package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

}
