package com.cct.beautysalon.repositories;

import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Book;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT b FROM Book b JOIN b.workerUser u WHERE u.id = :workerUserId and b.dateBook = :dateBook")
    List<Book> findByWorkerUserIdAndDateBook(long workerUserId, LocalDate dateBook);

//    @OrderBy("dateBook ASC, workerUser.id ASC, startTimeBook ASC")
//    List<Book> findAll();

}
