package com.cct.beautysalon.models;

import com.cct.beautysalon.enums.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    //private LocalDateTime dateOfAgenda;
    @NotNull
    private LocalDate dateBook;

    @NotNull
    private LocalTime startTimeBook;

    @NotNull
    private LocalTime finishTimeBook;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_user_id")
    private User clientUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "worker_user_id")
    private User workerUser;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private String observation;

    public Book(@NotNull Treatment treatment, @NotNull LocalDate dateBook, @NotNull LocalTime startTimeBook, @NotNull LocalTime finishTimeBook, @NotNull User clientUser, @NotNull User workerUser, @NotNull LocalDateTime createdDate, BookStatus status, String observation) {
        this.treatment = treatment;
        this.dateBook = dateBook;
        this.startTimeBook = startTimeBook;
        this.finishTimeBook = finishTimeBook;
        this.clientUser = clientUser;
        this.workerUser = workerUser;
        this.createdDate = createdDate;
        this.status = status;
        this.observation = observation;
    }
}
