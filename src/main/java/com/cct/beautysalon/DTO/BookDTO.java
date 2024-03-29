package com.cct.beautysalon.DTO;

import com.cct.beautysalon.enums.BookStatus;
import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Book;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

    @Autowired
    private static ModelMapper modelMapper;

    private Long id;
    private LocalDateTime dateOfAgenda;
    private Long treatmentId;

    private LocalDate dateBook;
    private LocalTime startTimeBook;
    private LocalTime finishTimeBook;
    private Long workerUserId;

    private String treatmentName;
    private String workerUserFirstName;
    private String workerUserLastName;
    private Long clientUserId;
    private String clientUserFirstName;
    private String clientUserLastName;
    private BookStatus status;
    private String observation;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime inServiceDate;
    private LocalDateTime completeDate;

    /**
     * convert to entity, once some entities can enter in a recursive manner, the mapper can't be used
     * @return
     */
    public Book toEntity() {
        Book book = new Book();
        book.setId(this.id);
        book.setDateBook(this.dateBook);
        book.setStartTimeBook(this.startTimeBook);
        book.setFinishTimeBook(this.finishTimeBook);

        Treatment treatment = new Treatment();
        treatment.setId(this.treatmentId);
        treatment.setName(this.treatmentName);
        book.setTreatment(treatment);


        User workerUser = User.builder().id(this.workerUserId)
                .firstName(this.workerUserFirstName)
                .lastName(this.workerUserLastName)
                .build();
        book.setWorkerUser(workerUser);

        User clientUser = User.builder()
                        .id(this.clientUserId)
                .firstName(this.clientUserFirstName)
                .lastName(this.clientUserLastName)
                        .build();
        book.setClientUser(clientUser);

        book.setStatus(this.status);
        book.setObservation(this.observation);
        book.setCreatedDate(this.createdDate);
        book.setUpdatedDate(this.updatedDate);
        book.setInServiceDate(this.inServiceDate);
        book.setCompleteDate(this.completeDate);

        return book;
    }

}
