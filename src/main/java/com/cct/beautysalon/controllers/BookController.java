package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.BookAvailableDTO;
import com.cct.beautysalon.DTO.BookDTO;
import com.cct.beautysalon.DTO.BookFilterParamsDTO;
import com.cct.beautysalon.DTO.BookSearchParamsDTO;
import com.cct.beautysalon.exceptions.BookingNotAvailableException;
import com.cct.beautysalon.exceptions.CantBeDeletedException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.services.BookService;
import com.cct.beautysalon.services.UserLoggedService;
import com.cct.beautysalon.utils.ErrorResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserLoggedService userLoggedService;

    /**
     * get books by filter
     *
     * @param filter
     * @return
     */
    @PostMapping("/withFilters")
    public List<BookDTO> getBooksWithFilters(@RequestBody BookFilterParamsDTO filter) {
        return bookService.findAllWithFilters(filter);
    }

    /**
     * get Books Completed filtered by ClientUserId And DateBook
     *
     * @param clientUserId
     * @return
     */
    @GetMapping("/completedBooksByClientToday")
    public List<BookDTO> getBooksCompletedByClientUserIdAndDateBook(@RequestParam Long clientUserId) {
        return bookService.findByClientUserIdAndStatusAndDateBook(clientUserId);

    }

    /**
     * Create a new book
     *
     * @param BookDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody BookDTO bookDTO) {
        try {
            return ResponseEntity.ok(bookService.save(bookDTO));
        } catch (BookingNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.GONE).body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Search the slots available for the given criteria
     *
     * @param bookSearchParams
     * @return
     */

    @PostMapping("/availability")
    public Map<String, BookAvailableDTO> getBookAvailable(@RequestBody BookSearchParamsDTO bookSearchParams) {
        return bookService.getBookAvailable(bookSearchParams);
    }

    /**
     * Update Book by id
     *
     * @param id
     * @param bookDTO
     */
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody BookDTO bookDTO) {
        if (!id.equals(bookDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book id doesn't match");
        }
        bookService.update(id, bookDTO);
    }

    /**
     * Delete a Book
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(new NotFoundException().getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(new CantBeDeletedException().getMessage()));
        }
    }
}
