package com.cct.beautysalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table
@DiscriminatorValue("Treatment")
public class Treatment extends Item{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String description;
//    private double price;
    private int duration;


    @ManyToOne
    @JoinColumn(name = "treatment_type_id")
    private TreatmentType type;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "availability_treatment",
//            joinColumns = @JoinColumn(name = "treatment_id"),
//            inverseJoinColumns = @JoinColumn(name = "availability_id")
//    )
    @JsonIgnore
    @ManyToMany(mappedBy = "treatments", fetch = FetchType.EAGER)
    private Set<Availability> availabilities;

    public Treatment(String name, String description, TreatmentType type, double price, int duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.duration = duration;
    }


    @Override
    //ignored the collections
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treatment treatment = (Treatment) o;
        return Double.compare(treatment.price, price) == 0 && duration == treatment.duration && Objects.equals(id, treatment.id) && Objects.equals(name, treatment.name) && Objects.equals(description, treatment.description) && Objects.equals(type, treatment.type);
    }

    @Override
    //ignored the collections
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, type);
    }

    //ver se irá funcionar, concatenando o pai com o filho
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        Treatment treatment = (Treatment) o;
//        return duration == treatment.duration && Objects.equals(type, treatment.type);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), duration, type);
//    }
}
