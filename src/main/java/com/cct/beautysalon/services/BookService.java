package com.cct.beautysalon.services;

import com.cct.beautysalon.DTO.*;
import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.enums.Role;
import com.cct.beautysalon.exceptions.BookingNotAvailableException;
import com.cct.beautysalon.exceptions.NotFoundException;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.User;
import com.cct.beautysalon.repositories.BookRepository;
import com.cct.beautysalon.repositories.specifications.BookSpecifications;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor

public class BookService {
    private final BookRepository bookRepository;
    private final UserLoggedService userLoggedService;
    private final ModelMapper mapper;
    private final AvailabilityService availabilityService;

    //convert the entity to DTO
    private BookDTO toDTO(Book book) {
        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        bookDTO.setTreatmentName(book.getTreatment().getName());
        return bookDTO;
    }

    //convert the DTO to entity
    private Book toEntity(BookDTO bookDTO) {
        Book book = mapper.map(bookDTO, Book.class);
        return book;
    }

    public Iterable<Book> findAll() {
        Sort sort = Sort.by("dateBook", "workerUser.id", "startTimeBook").ascending();
        return bookRepository.findAll(sort);
    }

    public Book findBookById(Long id) {
        return findOrThrowBookById(id);
    }

    public BookDTO save(BookDTO bookDTO) {
        Book book = toEntity(bookDTO);

        User userLogged = userLoggedService.getUserLogged();
        if (userLogged.getRole() != Role.CLIENT) {
            book.setClientUser(book.getClientUser());
        } else {
            book.setClientUser(userLogged);
        }
        book.setCreatedDate(LocalDateTime.now());
        //return bookRepository.save(book);

        //check if there is any conflict
        List<Book> conflictBook = this.findConflictBook(book);
        if (conflictBook.size() > 0) {
            System.out.println(" conflicts size = " + conflictBook.size());
            System.out.println("id = " + conflictBook.get(0).getId());
            throw new BookingNotAvailableException();
            //Sorry,this booking isn't available anymore, please choose another day, time and or professional
        }

        return toDTO(bookRepository.save(book));
    }

    public List<Book> findConflictBook(Book book) {
        System.out.println("findConflictBook **************************");
        return bookRepository.findConflictBook(book.getWorkerUser().getId(), book.getDateBook(), book.getStartTimeBook(), book.getFinishTimeBook());
    }

    public void delete(Long id) {
        findOrThrowBookById(id);
        bookRepository.deleteById(id);
    }

    public void update(Long id, BookDTO bookDTO) {
        Book book = toEntity(bookDTO);
        this.updateStatusDescription(id, bookDTO.getStatus(), bookDTO.getObservation());
    }

    public void updateStatusDescription(Long id, BookStatus status, String observation) {

        findOrThrowBookById(id);

        Book book = findOrThrowBookById(id);
        book.setStatus(status);
        book.setObservation(observation);
        book.setUpdatedDate(LocalDateTime.now());

        if (status == BookStatus.IN_SERVICE && book.getInServiceDate() == null) {
            book.setInServiceDate(LocalDateTime.now());
        } else if (status == BookStatus.COMPLETED && book.getCompleteDate() == null) {
            book.setCompleteDate(LocalDateTime.now());
        }

        bookRepository.save(book);
    }


//
//    public void update(Long id, Book book) {
//        findOrThrowBookById(id);
//        bookRepository.save(book);
//    }

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
        return bookRepository.findByWorkerUserId(workerUserId, sort);
    }

    public List<Book> findByClientUserId(Long clientUserId) {
        Sort sort = Sort.by("dateBook", "startTimeBook").ascending();
        return bookRepository.findByClientUserId(clientUserId, sort);
    }

    public List<BookDTO> findByClientUserIdAndStatusAndDateBook(Long clientUserId) {
        List<Book> books = new ArrayList<>();
        books.addAll(bookRepository.findByClientUserIdAndStatusAndDateBook(clientUserId, BookStatus.COMPLETED, LocalDate.now()));
        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public Map<String, BookAvailableDTO> getBookAvailable(BookSearchParamsDTO bookSearchParams) {
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
            List<Book> books = this.findByWorkerUserIdAndDateBook(a.getUser().getId(), dateBook);

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
                        startDateTimeShift = bookingFinished;
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
                    } else if ((startSlotTime.isAfter(bookingStarted) && startSlotTime.isBefore(bookingFinished)) ||
                            endSlotTime.isAfter(bookingStarted) && endSlotTime.isBefore(bookingFinished)) {
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


    public List<BookDTO> findAllWithFilters(BookFilterParamsDTO filter) {

        User userLogged = userLoggedService.getUserLogged();
        List<Book> books = new ArrayList<>();

        switch (userLogged.getRole()) {
            case WORKER: //only books for this worker
                filter.setWorkerId(userLogged.getId());
                break;
            case CLIENT: //only books for this client
                filter.setClientId(userLogged.getId());
                break;

        }
        Specification<Book> spec = BookSpecifications.withFilters(filter.getDateBook(),
                filter.getBookStatus(),
                filter.getClientId(),
                filter.getWorkerId(),
                filter.getFilterDateBy());
        Sort sort = Sort.by("dateBook", "startTimeBook", "workerUser.firstName").ascending();


        books.addAll(StreamSupport.stream(bookRepository.findAll(spec, sort).spliterator(), false).collect(Collectors.toList()));

        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
