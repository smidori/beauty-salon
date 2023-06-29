package com.cct.beautysalon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Builder
@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="agenda")
public class Agenda {

    public Agenda(Availability availability, LocalDateTime dateOfAgenda) {
        this.availability = availability;
        this.dateOfAgenda = dateOfAgenda;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;
    private LocalDateTime dateOfAgenda;
}
