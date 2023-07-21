package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private Long id;
    //private Availability availability;
    private LocalDateTime dateOfAgenda;
    //private Treatment treatment;
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
}
