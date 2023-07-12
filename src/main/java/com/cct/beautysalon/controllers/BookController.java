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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
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

//    @GetMapping
//    public List<BookDTO> getAvailabilities() {
//        var availabilities = StreamSupport.stream(bookService.findAll().spliterator(), false)
//                .collect(Collectors.toList());
//        return availabilities.stream().map(this::toDTO).toList();
//    }

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
    @PostMapping("/availability")
    public Map<String, BookAvailableDTO> getBookAvailable(@RequestBody BookSearchParamsDTO bookSearchParams) {
        //public BookSearchParamsDTO getBookAvailable() {
        //Book book = bookService.findBookById(id);

        Map<String, BookAvailableDTO> bookAvailable = new HashMap<>();

        //Date dateBook = new Date();
        LocalDate dateBook = LocalDate.now(); //Get the date choosen by the user
        long idTreatment = bookSearchParams.getTreatment().getId();
        Long idUser = null;

        if(bookSearchParams.getUser() != null){
            idUser = bookSearchParams.getUser().getId();
        }

        int duration = bookSearchParams.getTreatment().getDuration();

        List<Availability> availabilities = availabilityService.findByTreatmentId(idTreatment);
        for (Availability a : availabilities) {
            //UserDTO userDTO = new UserDTO(a.getUser().getId(), a.getUser().getFirstName(), a.getUser().getLastName());


            int hourStartShift = a.getHourStartTime().getHours();
            int minuteStartShift = a.getHourStartTime().getMinutes();

            int hourEndShift = a.getHourFinishTime().getHours();
            int minuteEndShift = a.getHourFinishTime().getMinutes();

            //CHECK IF IS POSSIBLE TO CHANGE FROM hourStartTime to LocalTime
            LocalTime startTimeShift = LocalTime.of(hourStartShift, minuteStartShift); // Horário de início do trabalho
            LocalTime endTimeShift = LocalTime.of(hourEndShift, minuteEndShift); // Horário de término do trabalho

            List<LocalDateTime> availableDateTimeList = new ArrayList<>();

            LocalDateTime startDateTimeShift = LocalDateTime.of(dateBook, startTimeShift);
            LocalDateTime endDateTimeShift = LocalDateTime.of(dateBook, endTimeShift);


            LocalDate currentDate = startDateTimeShift.toLocalDate();
            LocalDateTime currentStartDateTime = LocalDateTime.of(currentDate, startTimeShift);
            LocalDateTime currentEndDateTime = LocalDateTime.of(currentDate, endTimeShift);

            while (startDateTimeShift.isBefore(endDateTimeShift)) {
                System.out.println(startDateTimeShift + "is before " + endDateTimeShift);
//                if (startDateTimeShift.toLocalDate().isAfter(currentDate)) {
//                    currentStartDateTime = LocalDateTime.of(currentDate, startTimeShift);
//                    currentEndDateTime = LocalDateTime.of(currentDate, endTimeShift);
//                }
                LocalDateTime endSlotTime = startDateTimeShift.plusMinutes(duration);
                if(startDateTimeShift.isBefore(currentEndDateTime) && (endSlotTime.isBefore(currentEndDateTime) || endSlotTime.isEqual(currentEndDateTime)) ){
                    Set<BookDetailsDTO> bookDetailsDTOS = new TreeSet<>();
                    //Set<BookDetailsDTO> bookDetailsDTOS = new HashSet<>();

                    String key = java.sql.Time.valueOf(startDateTimeShift.toLocalTime())+"";

                    BookAvailableDTO bookAvailableDTO = bookAvailable.get(key);
                    if(bookAvailableDTO == null){

                        bookAvailableDTO = BookAvailableDTO.builder()
                                .dateBook((java.sql.Date.valueOf(currentDate)))
                                .startTimeBook(java.sql.Time.valueOf(startDateTimeShift.toLocalTime()) )
                                .finishTimeBook( java.sql.Time.valueOf(endSlotTime.toLocalTime()) )
                                .build();
                        bookAvailable.put(key, bookAvailableDTO);
                    }else{
                        System.out.println("************ achou ************");
                        bookDetailsDTOS = bookAvailableDTO.getBookDetails();
                        System.out.println("bookDetailsDTOS => " + bookDetailsDTOS);
                        System.out.println("length before: " + bookDetailsDTOS.size());
                    }


                    BookDetailsDTO slot = BookDetailsDTO.builder().dateBook(java.sql.Date.valueOf(currentDate))
                            .startTimeBook(java.sql.Time.valueOf(startDateTimeShift.toLocalTime()) )
                            .finishTimeBook( java.sql.Time.valueOf(endSlotTime.toLocalTime()) )
                            .userId(a.getUser().getId())
                            .userName(a.getUser().getFirstName() + " " + a.getUser().getLastName())
                            .build();

                    System.out.println("slot: " + slot);
                    bookDetailsDTOS.add(slot);

                    System.out.println("length after: " + bookDetailsDTOS.size());
                    System.out.println("bookDetailsDTOS => " + bookDetailsDTOS);
                    bookAvailableDTO.setBookDetails(bookDetailsDTOS);

                    System.out.println(startDateTimeShift + " to " + endSlotTime);
                    startDateTimeShift = endSlotTime;
                }else{
                    //bookAvailable.add(bookDetailsDTOS);
                    startDateTimeShift = endSlotTime;
                }

//                if ((startDateTimeShift.isAfter(currentStartDateTime) || startDateTimeShift.isEqual(currentStartDateTime)) &&
//                        (startDateTimeShift.isBefore(currentEndDateTime) || startDateTimeShift.isEqual(currentEndDateTime))) {
//                    System.out.println(currentStartDateTime + " <= " + startDateTimeShift + "<= " + currentEndDateTime);
//                    availableDateTimeList.add(startDateTimeShift);
//                }
                //startDateTimeShift = startDateTimeShift.plusMinutes(30);
            }
//            for(LocalDateTime dateTime : availableDateTimeList){
//
//            }


        }
//
//        Availability availability = new Availability();
//        availability.setId(1L);
//        for (LocalDateTime dateTime : availableDateTimeList) {
//            // Salvar o registro no banco de dados
//            Book book = new Book(availability,dateTime);
//            bookService.save(book);
//        }

        TreeMap<String, BookAvailableDTO> sorted = new TreeMap<>();
        sorted.putAll(bookAvailable);

        return sorted;
    }

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
