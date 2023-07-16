package com.cct.beautysalon.controllers;

import com.cct.beautysalon.DTO.BookAvailableDTO;
import com.cct.beautysalon.DTO.BookDTO;
import com.cct.beautysalon.DTO.BookDetailsDTO;
import com.cct.beautysalon.DTO.BookSearchParamsDTO;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.services.AvailabilityService;
import com.cct.beautysalon.services.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AvailabilityService availabilityService;

    private final ModelMapper mapper;

    //convert the entity to DTO
    private BookDTO toDTO(Book book) {
        return mapper.map(book, BookDTO.class);
    }

    //convert the DTO to entity
    private Book toEntity(BookDTO bookDTO) {
        return mapper.map(bookDTO, Book.class);
    }

    //TODO: FILTRAR OS BOOKS DO USUÁRIO QUANDO FOR CLIENT E ADMIN TRAZER TODOS E WORKER TRAZER OS SEUS PRÓPRIOS
    @GetMapping
    public List<BookDTO> getAvailabilities() {
        var availabilities = StreamSupport.stream(bookService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return availabilities.stream().map(this::toDTO).toList();
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
     * @param bookSearchParams
     * @return
     */
    @PostMapping("/availability")
    public Map<String, BookAvailableDTO> getBookAvailable(@RequestBody BookSearchParamsDTO bookSearchParams) {
        LocalDate dateBook = bookSearchParams.getDateBook();
        long idTreatment = bookSearchParams.getTreatment().getId();
        Long idUser = bookSearchParams.getUser() != null ? bookSearchParams.getUser().getId() : null;
        int duration = bookSearchParams.getTreatment().getDuration();

        List<Availability> availabilities = availabilityService.findByTreatmentId(idTreatment);
        Map<String, BookAvailableDTO> bookAvailable = new TreeMap<>();

        for (Availability a : availabilities) {
            LocalTime startTimeShift = a.getHourStartTime().toLocalTime();
            LocalTime endTimeShift = a.getHourFinishTime().toLocalTime();
            LocalDateTime startDateTimeShift = LocalDateTime.of(dateBook, startTimeShift);
            LocalDateTime endDateTimeShift = LocalDateTime.of(dateBook, endTimeShift);

            //check from here to down
            List<Book> books = bookService.findByWorkerUserId(a.getUser().getId(), dateBook);
            boolean hasBookingInInterval = false;
            for (Book book : books) {
                LocalDateTime bookingStartTime = LocalDateTime.of(book.getDateBook(), book.getStartTimeBook());
                LocalDateTime bookingEndTime = LocalDateTime.of(book.getDateBook(), book.getFinishTimeBook());
                if ((startDateTimeShift.isBefore(bookingEndTime) || startDateTimeShift.isEqual(bookingEndTime)) &&
                        (endDateTimeShift.isAfter(bookingStartTime) || endDateTimeShift.isEqual(bookingStartTime))) {
                    hasBookingInInterval = true;
                    break;
                }
            }

            while (startDateTimeShift.isBefore(endDateTimeShift)) {
                LocalDateTime endSlotTime = startDateTimeShift.plusMinutes(duration);
                if (!hasBookingInInterval && startDateTimeShift.isBefore(endDateTimeShift) && (endSlotTime.isBefore(endDateTimeShift) || endSlotTime.isEqual(endDateTimeShift))) {
                    String key = startDateTimeShift.toLocalTime() + "";
                    LocalDateTime finalStartDateTimeShift = startDateTimeShift;
                    BookAvailableDTO bookAvailableDTO = bookAvailable.computeIfAbsent(key, k ->
                            BookAvailableDTO.builder()
                                    .dateBook(dateBook)
                                    .startTimeBook(finalStartDateTimeShift.toLocalTime())
                                    .finishTimeBook(endSlotTime.toLocalTime())
                                    .build()
                    );

                    Set<BookDetailsDTO> bookDetailsDTOS = new TreeSet<>(Comparator.comparing(BookDetailsDTO::getStartTimeBook));
                    bookDetailsDTOS.addAll(bookAvailableDTO.getBookDetails());

                    BookDetailsDTO slot = BookDetailsDTO.builder()
                            .dateBook(dateBook)
                            .startTimeBook(startDateTimeShift.toLocalTime())
                            .finishTimeBook(endSlotTime.toLocalTime())
                            .userId(a.getUser().getId())
                            .userName(a.getUser().getFirstName() + " " + a.getUser().getLastName())
                            .build();

                    bookDetailsDTOS.add(slot);
                    bookAvailableDTO.setBookDetails(bookDetailsDTOS);

                    startDateTimeShift = endSlotTime;
                } else {
                    startDateTimeShift = endSlotTime;
                }
            }
        }

        return bookAvailable;
    }

//    public Map<String, BookAvailableDTO> getBookAvailable(@RequestBody BookSearchParamsDTO bookSearchParams) {
//        LocalDate dateBook = bookSearchParams.getDateBook();
//        long idTreatment = bookSearchParams.getTreatment().getId();
//        Long idUser = bookSearchParams.getUser() != null ? bookSearchParams.getUser().getId() : null;
//        int duration = bookSearchParams.getTreatment().getDuration();
//
//        List<Availability> availabilities = availabilityService.findByTreatmentId(idTreatment);
//        Map<String, BookAvailableDTO> bookAvailable = new TreeMap<>();
//
//        for (Availability a : availabilities) {
//            LocalDateTime startDateTimeShift = LocalDateTime.of(dateBook, a.getHourStartTime().toLocalTime());
//            LocalDateTime endDateTimeShift = LocalDateTime.of(dateBook, a.getHourFinishTime().toLocalTime());
//
//            //get the bookings that is already done for this user in this day
//            //find book by the worker
//            List<Book> books = bookService.findByWorkerUserId(a.getUser().getId(), dateBook);
//            for (Book book : books) {
//                book.getDateBook();
//                book.getStartTimeBook();
//                book.getFinishTimeBook();
//            }
//
//            while (startDateTimeShift.isBefore(endDateTimeShift)) {
//                LocalDateTime endSlotTime = startDateTimeShift.plusMinutes(duration);
//                if (startDateTimeShift.isBefore(endDateTimeShift) && (endSlotTime.isBefore(endDateTimeShift) || endSlotTime.isEqual(endDateTimeShift))) {
//                    String key = startDateTimeShift.toLocalTime() + "";
//                    LocalDateTime finalStartDateTimeShift = startDateTimeShift;
//                    BookAvailableDTO bookAvailableDTO = bookAvailable.computeIfAbsent(key, k ->
//                            BookAvailableDTO.builder()
//                                    .dateBook(dateBook)
//                                    .startTimeBook(finalStartDateTimeShift.toLocalTime())
//                                    .finishTimeBook(endSlotTime.toLocalTime())
//                                    .build()
//                    );
//
//                    Set<BookDetailsDTO> bookDetailsDTOS = new TreeSet<>(Comparator.comparing(BookDetailsDTO::getStartTimeBook));
//                    bookDetailsDTOS.addAll(bookAvailableDTO.getBookDetails());
//
//                    BookDetailsDTO slot = BookDetailsDTO.builder()
//                            .dateBook(dateBook)
//                            .startTimeBook(startDateTimeShift.toLocalTime())
//                            .finishTimeBook(endSlotTime.toLocalTime())
//                            .userId(a.getUser().getId())
//                            .userName(a.getUser().getFirstName() + " " + a.getUser().getLastName())
//                            .build();
//
//                    bookDetailsDTOS.add(slot);
//                    bookAvailableDTO.setBookDetails(bookDetailsDTOS);
//
//                    startDateTimeShift = endSlotTime;
//                } else {
//                    startDateTimeShift = endSlotTime;
//                }
//            }
//        }
//
//        return bookAvailable;
//    }


//    @PostMapping("/availability")
//    public Map<String, BookAvailableDTO> getBookAvailable(@RequestBody BookSearchParamsDTO bookSearchParams) {
//        //public BookSearchParamsDTO getBookAvailable() {
//        //Book book = bookService.findBookById(id);
//
//        LocalDateTime lt
//                = LocalDateTime.now();
//
//        // print result
//        System.out.println("---------------LocalDateTime : "
//                + lt);
//        System.out.println("BookSearchParamsDTO ==============>  " + bookSearchParams);
//        Map<String, BookAvailableDTO> bookAvailable = new HashMap<>();
//
//        //Date dateBook = new Date();
//        //LocalDate dateBook = LocalDate.now(); //Get the date choosen by the user
//        LocalDate dateBook = bookSearchParams.getDateBook();
//        long idTreatment = bookSearchParams.getTreatment().getId();
//        Long idUser = null;
//
//        if(bookSearchParams.getUser() != null){
//            idUser = bookSearchParams.getUser().getId();
//        }
//
//        int duration = bookSearchParams.getTreatment().getDuration();
//
//        List<Availability> availabilities = availabilityService.findByTreatmentId(idTreatment);
//        for (Availability a : availabilities) {
//            //UserDTO userDTO = new UserDTO(a.getUser().getId(), a.getUser().getFirstName(), a.getUser().getLastName());
//
//
//            int hourStartShift = a.getHourStartTime().getHours();
//            int minuteStartShift = a.getHourStartTime().getMinutes();
//
//            int hourEndShift = a.getHourFinishTime().getHours();
//            int minuteEndShift = a.getHourFinishTime().getMinutes();
//
//            //CHECK IF IS POSSIBLE TO CHANGE FROM hourStartTime to LocalTime
//            LocalTime startTimeShift = LocalTime.of(hourStartShift, minuteStartShift); // Horário de início do trabalho
//            LocalTime endTimeShift = LocalTime.of(hourEndShift, minuteEndShift); // Horário de término do trabalho
//
//            List<LocalDateTime> availableDateTimeList = new ArrayList<>();
//
//            LocalDateTime startDateTimeShift = LocalDateTime.of(dateBook, startTimeShift);
//            LocalDateTime endDateTimeShift = LocalDateTime.of(dateBook, endTimeShift);
//
//
//            LocalDate currentDate = startDateTimeShift.toLocalDate();
//            LocalDateTime currentStartDateTime = LocalDateTime.of(currentDate, startTimeShift);
//            LocalDateTime currentEndDateTime = LocalDateTime.of(currentDate, endTimeShift);
//
//            while (startDateTimeShift.isBefore(endDateTimeShift)) {
//                //System.out.println(startDateTimeShift + "is before " + endDateTimeShift);
////                if (startDateTimeShift.toLocalDate().isAfter(currentDate)) {
////                    currentStartDateTime = LocalDateTime.of(currentDate, startTimeShift);
////                    currentEndDateTime = LocalDateTime.of(currentDate, endTimeShift);
////                }
//                LocalDateTime endSlotTime = startDateTimeShift.plusMinutes(duration);
//                if(startDateTimeShift.isBefore(currentEndDateTime) && (endSlotTime.isBefore(currentEndDateTime) || endSlotTime.isEqual(currentEndDateTime)) ){
//                    Set<BookDetailsDTO> bookDetailsDTOS = new TreeSet<>();
//                    //Set<BookDetailsDTO> bookDetailsDTOS = new HashSet<>();
//
//                    String key = java.sql.Time.valueOf(startDateTimeShift.toLocalTime())+"";
//
//                    BookAvailableDTO bookAvailableDTO = bookAvailable.get(key);
//                    if(bookAvailableDTO == null){
//
//                        bookAvailableDTO = BookAvailableDTO.builder()
//                                //.dateBook((java.sql.Date.valueOf(currentDate)))
//                                .dateBook(dateBook)
//                                .startTimeBook(startDateTimeShift.toLocalTime())
//                                .finishTimeBook(endSlotTime.toLocalTime())
//                                .build();
//                        bookAvailable.put(key, bookAvailableDTO);
//                    }else{
//                        bookDetailsDTOS = bookAvailableDTO.getBookDetails();
////                        System.out.println("bookDetailsDTOS => " + bookDetailsDTOS);
////                        System.out.println("length before: " + bookDetailsDTOS.size());
//                    }
//
//
//                    BookDetailsDTO slot = BookDetailsDTO.builder().dateBook(dateBook)
//                            .startTimeBook(startDateTimeShift.toLocalTime() )
//                            .finishTimeBook(endSlotTime.toLocalTime())
//                            .userId(a.getUser().getId())
//                            .userName(a.getUser().getFirstName() + " " + a.getUser().getLastName())
//                            .build();
//
//                    bookDetailsDTOS.add(slot);
//                    bookAvailableDTO.setBookDetails(bookDetailsDTOS);
//
//                    //System.out.println(startDateTimeShift + " to " + endSlotTime);
//                    startDateTimeShift = endSlotTime;
//                }else{
//                    //bookAvailable.add(bookDetailsDTOS);
//                    startDateTimeShift = endSlotTime;
//                }
//
////                if ((startDateTimeShift.isAfter(currentStartDateTime) || startDateTimeShift.isEqual(currentStartDateTime)) &&
////                        (startDateTimeShift.isBefore(currentEndDateTime) || startDateTimeShift.isEqual(currentEndDateTime))) {
////                    System.out.println(currentStartDateTime + " <= " + startDateTimeShift + "<= " + currentEndDateTime);
////                    availableDateTimeList.add(startDateTimeShift);
////                }
//                //startDateTimeShift = startDateTimeShift.plusMinutes(30);
//            }
////            for(LocalDateTime dateTime : availableDateTimeList){
////
////            }
//
//
//        }
////
////        Availability availability = new Availability();
////        availability.setId(1L);
////        for (LocalDateTime dateTime : availableDateTimeList) {
////            // Salvar o registro no banco de dados
////            Book book = new Book(availability,dateTime);
////            bookService.save(book);
////        }
//
//        TreeMap<String, BookAvailableDTO> sorted = new TreeMap<>();
//        sorted.putAll(bookAvailable);
//
//        return sorted;
//    }

    /**
     * Update Book by id
     *
     * @param id
     * @param bookDTO
     */
//    @PutMapping("/{id}")
//    public void update(@PathVariable("id") Long id, @Valid @RequestBody BookDTO bookDTO) {
//        if (!id.equals(bookDTO.getId())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book id doesn't match");
//        }
//        Book book = toEntity(bookDTO);
//        bookService.update(id, book);
//    }

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
