package com.cct.beautysalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table
@DiscriminatorValue("Treatment")
// Indica o uso de "item_type" para identificar o tipo de objeto
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "item_type", visible = true)


public class Treatment extends Item{

    private int duration;
    @ManyToOne
    @JoinColumn(name = "treatment_type_id")
    private TreatmentType type;

    @ManyToMany(mappedBy = "treatments", fetch = FetchType.LAZY)
    private Set<Availability> availabilities;


    //constructor
    public Treatment(long id, String name){
        this.id = id;
        this.name = name;
    }

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

}
