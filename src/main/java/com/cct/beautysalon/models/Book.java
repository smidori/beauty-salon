package com.cct.beautysalon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;


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

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    private LocalDateTime dateOfAgenda;

    @Column
    private Date dateBook;

    @Column
    private Time startTimeBook;

    @Column
    private Time finishTimeBook;

    @Column
    private User clientUser;

}
