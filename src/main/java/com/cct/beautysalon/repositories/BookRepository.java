package com.cct.beautysalon.repositories;

import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.models.Book;
import jakarta.persistence.OrderBy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b JOIN b.workerUser u WHERE u.id = :workerUserId and b.dateBook = :dateBook")
    List<Book> findByWorkerUserIdAndDateBook(long workerUserId, LocalDate dateBook);

    @Query("SELECT b FROM Book b JOIN b.workerUser u WHERE u.id = :workerUserId")
    List<Book> findByWorkerUserId(long workerUserId, Sort sort);

    @Query("SELECT b FROM Book b JOIN b.clientUser u WHERE u.id = :clientUserId")
    List<Book> findByClientUserId(long clientUserId, Sort sort);

    @Query("SELECT b FROM Book b JOIN b.clientUser u WHERE u.id = :clientUserId and b.status = :status and b.dateBook = :dateBook")
    List<Book> findByClientUserIdAndStatusAndDateBook(long clientUserId, BookStatus status, LocalDate dateBook);

    //@OrderBy("dateBook ASC, workerUser.id ASC, startTimeBook ASC")
    List<Book> findAll(Specification<Book> specification,Sort sort);

}
