package com.cct.beautysalon.services;

import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.BookRepository;
import com.cct.beautysalon.repositories.specifications.BookSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoggedService userLoggedService;

    public Iterable<Book> findAll() {
        Sort sort = Sort.by("dateBook", "workerUser.id", "startTimeBook").ascending();
        return bookRepository.findAll(sort);
    }

    public Book findBookById(Long id) {
        return findOrThrowBookById(id);
    }

    public Book save(Book book) {
        System.out.println("+++++++++++++++++++ book save+++++++++++++++++++++++++++++");
        User userLogged = userLoggedService.getUserLogged();
        System.out.println("Dados do usuário logado: " + userLogged.getId() + userLogged.getFirstName());
        book.setClientUser(userLogged);
        book.setCreatedDate(LocalDateTime.now());
        System.out.println("Dados userLogged: " + userLogged);
        System.out.println("Dados booking: " + book);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        findOrThrowBookById(id);
        bookRepository.deleteById(id);
    }

    public void update(Long id, Book book) {
        findOrThrowBookById(id);
        bookRepository.save(book);
    }

    private Book findOrThrowBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Book by id: " + id + "was not found"));
    }

    public List<Book> findByWorkerUserIdAndDateBook(Long workerUserId, LocalDate dateBook) {
        return bookRepository.findByWorkerUserIdAndDateBook(workerUserId, dateBook);
    }

    public List<Book> findByWorkerUserId(Long workerUserId) {
        Sort sort = Sort.by("dateBook", "startTimeBook").ascending();
        return bookRepository.findByWorkerUserId(workerUserId,sort);
    }

    public List<Book> findByClientUserId(Long clientUserId) {
        Sort sort = Sort.by("dateBook", "startTimeBook").ascending();
        return bookRepository.findByClientUserId(clientUserId, sort);
    }

    public List<Book> findByClientUserIdAndStatusAndDateBook(Long clientUserId, BookStatus status, LocalDate dateBook) {
        return bookRepository.findByClientUserIdAndStatusAndDateBook(clientUserId,status,dateBook);
    }


    public List<Book> findAllWithFilters(LocalDate date, BookStatus status, Long clientId, Long workerId) {
        Specification<Book> spec = BookSpecifications.withFilters(date, status, clientId, workerId);
        Sort sort = Sort.by("dateBook","startTimeBook", "workerUser.firstName").ascending();
        return bookRepository.findAll(spec, sort);
    }
}
