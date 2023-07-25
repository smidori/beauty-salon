package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.BookAvailableDTO;
import com.cct.beautysalon.DTO.BookDTO;
import com.cct.beautysalon.DTO.BookDetailsDTO;
import com.cct.beautysalon.DTO.BookSearchParamsDTO;
import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.services.AvailabilityService;
import com.cct.beautysalon.services.BookService;
import com.cct.beautysalon.services.UserLoggedService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AvailabilityService availabilityService;
    private final UserLoggedService userLoggedService;

    private final ModelMapper mapper;

    //convert the entity to DTO
    private BookDTO toDTO(Book book) {
        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        bookDTO.setTreatmentName(book.getTreatment().getName());


        return bookDTO;
    }

    //convert the DTO to entity
    private Book toEntity(BookDTO bookDTO) {
        Book book = mapper.map(bookDTO, Book.class);
        //book.setStatus(BookStatus.valueOf(bookDTO.getStatus()));

        return book;
    }

    //TODO: FILTRAR OS BOOKS DO USUÁRIO QUANDO FOR CLIENT E ADMIN TRAZER TODOS E WORKER TRAZER OS SEUS PRÓPRIOS
    @GetMapping
    public List<BookDTO> getBooks() {
        User userLogged = userLoggedService.getUserLogged();
        List<Book> books = new ArrayList<>();

        switch (userLogged.getRole()) {
            case ADMIN:
                books.addAll(StreamSupport.stream(bookService.findAll().spliterator(), false).collect(Collectors.toList()));
                break;
            case WORKER:
                books.addAll(bookService.findByWorkerUserId(userLogged.getId()));
                break;
            case CLIENT:
                books.addAll(bookService.findByClientUserId(userLogged.getId()));
                break;
            default:
                // Caso a role não corresponda a nenhuma das opções acima, retornar uma lista vazia.
                return new ArrayList<>();
        }

        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/completedBooksByClientToday")
    public List<BookDTO> getBooksCompletedByClientUserIdAndDateBook(@RequestParam Long clientUserId) {
        List<Book> books = new ArrayList<>();
        books.addAll(bookService.findByClientUserIdAndStatusAndDateBook(clientUserId, BookStatus.COMPLETED.toString(), LocalDate.now()));
        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new book
     *
     * @param BookDTO
     * @return
     */
    @PostMapping
    public BookDTO save(@Valid @RequestBody BookDTO BookDTO) {
        Book book = toEntity(BookDTO);
        Book bookSaved = bookService.save(book);
        return toDTO(bookSaved);
    }

    /**
     * Get Book by id
     *
     * @param id
     * @return
     */
//    @GetMapping("/{id}")
//    public BookDTO getBookById(@PathVariable("id") Long id) {
//        return toDTO(bookService.findBookById(id));
//    }

    /**
     * Search the slots available for the given criteria
     *
     * @param bookSearchParams
     * @return
     */
    @PostMapping("/availability")
    public Map<String, BookAvailableDTO> getBookAvailable(@RequestBody BookSearchParamsDTO bookSearchParams) {
        LocalDate dateBook = bookSearchParams.getDateBook();
        long idTreatment = bookSearchParams.getTreatment().getId();
        Long idUser = bookSearchParams.getUser() != null ? bookSearchParams.getUser().getId() : null;
        int duration = bookSearchParams.getTreatment().getDuration();

        List<Availability> availabilities = availabilityService.findByTreatmentId(idTreatment, dateBook);
        Map<String, BookAvailableDTO> bookAvailable = new TreeMap<>();

        for (Availability a : availabilities) {
            LocalDateTime startDateTimeShift = LocalDateTime.of(dateBook, a.getHourStartTime().toLocalTime());

            LocalDateTime endDateTimeShift = LocalDateTime.of(dateBook, a.getHourFinishTime().toLocalTime());

            //check from here to down
            List<Book> books = bookService.findByWorkerUserIdAndDateBook(a.getUser().getId(), dateBook);
            System.out.println("Availability user" + a.getUser().getId() + " - " + a.getUser().getFirstName() + " " + a.getUser().getLastName());
            System.out.println("Book size = " + books.size());

            while (startDateTimeShift.isBefore(endDateTimeShift)) {
                LocalDateTime startSlotTime = startDateTimeShift;
                LocalDateTime endSlotTime = startSlotTime.plusMinutes(duration);

                boolean hasBookingInInterval = false;
                for (Book book : books) {
                    LocalDateTime bookingStarted = LocalDateTime.of(book.getDateBook(), book.getStartTimeBook());
                    LocalDateTime bookingFinished = LocalDateTime.of(book.getDateBook(), book.getFinishTimeBook());

                    System.out.println("Slot: " + startSlotTime + " ~ " + endSlotTime);
                    System.out.println("Booked: " + bookingStarted + " ~ " + bookingFinished);
                    if ((isAfterOrEqual(startSlotTime, bookingStarted) && startSlotTime.isBefore(bookingFinished)) &&
                            (isAfterOrEqual(endSlotTime, bookingStarted) && isBeforeOrEqual(endSlotTime, bookingFinished))
                    ) {
                        startDateTimeShift =    bookingFinished;
                        hasBookingInInterval = true;
                        System.out.println("hasBookingInInterval A startDateTimeShift =  " + startDateTimeShift);
                        break;
                    } else if (isAfterOrEqual(startSlotTime, bookingStarted) && isBeforeOrEqual(endSlotTime, bookingFinished)) {
                        startDateTimeShift = bookingFinished;
                        hasBookingInInterval = true;
                        System.out.println("hasBookingInInterval B startDateTimeShift =  " + startDateTimeShift);
                        break;
                    } else if (isBeforeOrEqual(startSlotTime, bookingStarted) && isAfterOrEqual(endSlotTime, bookingFinished)) {
                        startDateTimeShift = bookingFinished;
                        hasBookingInInterval = true;
                        System.out.println("hasBookingInInterval C startDateTimeShift = " + startDateTimeShift);
                        break;
                    } else if ((isAfterOrEqual(startSlotTime, bookingStarted) && startSlotTime.isBefore(bookingFinished)) &&
                            isAfterOrEqual(endSlotTime, bookingFinished)) {
                        startDateTimeShift = endSlotTime;
                        System.out.println("hasBookingInInterval D startDateTimeShift = " + startDateTimeShift);
                        hasBookingInInterval = true;
                        break;
                    } else if (startSlotTime.isEqual(bookingStarted) && endSlotTime.isEqual(bookingFinished)) {
                        startDateTimeShift = endSlotTime;
                        System.out.println("hasBookingInInterval E startDateTimeShift = " + startDateTimeShift);
                        hasBookingInInterval = true;
                        break;
                    }else if((startSlotTime.isAfter(bookingStarted) && startSlotTime.isBefore(bookingFinished)) ||
                              endSlotTime.isAfter(bookingStarted) && endSlotTime.isBefore(bookingFinished)){
                        startDateTimeShift = endSlotTime;
                        System.out.println("hasBookingInInterval F startDateTimeShift = " + startDateTimeShift);
                        hasBookingInInterval = true;
                        break;
                    }
                }
                if (!hasBookingInInterval) {
                    if (isAfterOrEqual(startSlotTime, startDateTimeShift) &&
                            isBeforeOrEqual(endSlotTime, endDateTimeShift)) {
                        String key = startSlotTime.toLocalTime() + "";
                        LocalDateTime finalStartDateTimeShift = startSlotTime;
                        BookAvailableDTO bookAvailableDTO = bookAvailable.computeIfAbsent(key, k ->
                                BookAvailableDTO.builder()
                                        .dateBook(dateBook)
                                        .startTimeBook(finalStartDateTimeShift.toLocalTime())
                                        .finishTimeBook(endSlotTime.toLocalTime())
                                        .build()
                        );

                        Set<BookDetailsDTO> bookDetailsDTOS = new TreeSet<>(Comparator.comparing(BookDetailsDTO::getUserId));
                        //System.out.println("bookAvailableDTO.getBookDetails() =====> " + bookAvailableDTO.getBookDetails());
                        if (bookAvailableDTO.getBookDetails() != null) {
                            bookDetailsDTOS.addAll(bookAvailableDTO.getBookDetails());
                        }


                        BookDetailsDTO slot = BookDetailsDTO.builder()
                                .dateBook(dateBook)
                                .startTimeBook(startSlotTime.toLocalTime())
                                .finishTimeBook(endSlotTime.toLocalTime())
                                .userId(a.getUser().getId())
                                .userName(a.getUser().getFirstName() + " " + a.getUser().getLastName())
                                .build();

                        bookDetailsDTOS.add(slot);
                        bookAvailableDTO.setBookDetails(bookDetailsDTOS);
                    }
                    startDateTimeShift = endSlotTime;
                }

            }
        }

        return bookAvailable;
    }

    private boolean isAfterOrEqual(LocalDateTime dateA, LocalDateTime dateB) {
        if (dateA.isAfter(dateB) || dateA.isEqual(dateB)) {
            return true;
        }
        return false;
    }

    private boolean isBeforeOrEqual(LocalDateTime dateA, LocalDateTime dateB) {
        if (dateA.isBefore(dateB) || dateA.isEqual(dateB)) {
            return true;
        }
        return false;
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
        Book book = toEntity(bookDTO);
        bookService.update(id, book);
    }

    /**
     * Delete a Book
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }
}
