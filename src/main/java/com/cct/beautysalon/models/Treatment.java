package com.cct.beautysalon.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table
public class Treatment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double price;

    private int duration;

    @ManyToOne
    @JoinColumn(name = "treatment_type_id")
    private TreatmentType type;

    @ManyToMany(mappedBy = "treatments", fetch = FetchType.EAGER)
    private Set<Availability> availabilities;

    public Treatment(String name, String description, TreatmentType type, double price, int duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.duration = duration;
    }



}
