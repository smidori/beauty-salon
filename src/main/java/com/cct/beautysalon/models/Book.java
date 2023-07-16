package com.cct.beautysalon.models;

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
}
