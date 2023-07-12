package com.cct.beautysalon.services;

import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Agenda;
import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return findOrThrowBookById(id);
    }

    public Book save(Book book) {
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
}
