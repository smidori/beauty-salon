package com.cct.beautysalon.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="availability")
public class Availability{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "availability_treatment",
            joinColumns = @JoinColumn(name = "availability_id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id")
    )
    @Column(nullable = false)
    private Set<Treatment> treatments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Column(nullable = false)
    private User user;


    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate finishDate;

    @Column(nullable = false)
    private Time hourStartTime;
    @Column(nullable = false)
    private Time hourFinishTime;

    public Availability(Set<Treatment> treatments, User user, LocalDate startDate, LocalDate finishDate, Time hourStartTime, Time hourFinishTime) {
        this.treatments = treatments;
        this.user = user;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.hourStartTime = hourStartTime;
        this.hourFinishTime = hourFinishTime;
    }

    @Override
    //ignore the collections
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(startDate, that.startDate) && Objects.equals(finishDate, that.finishDate) && Objects.equals(hourStartTime, that.hourStartTime) && Objects.equals(hourFinishTime, that.hourFinishTime);
    }

    @Override
    //ignore the collections
    public int hashCode() {
        return Objects.hash(id, user, startDate, finishDate, hourStartTime, hourFinishTime);
    }

}
