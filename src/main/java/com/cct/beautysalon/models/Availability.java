package com.cct.beautysalon.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.*;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="availability")
public class Availability{
    @Override
    //ignore the collections
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(monday, that.monday) && Objects.equals(tuesday, that.tuesday) && Objects.equals(wednesday, that.wednesday) && Objects.equals(thursday, that.thursday) && Objects.equals(friday, that.friday) && Objects.equals(saturday, that.saturday) && Objects.equals(sunday, that.sunday) && Objects.equals(startDate, that.startDate) && Objects.equals(finishDate, that.finishDate) && Objects.equals(lunchstarttime, that.lunchstarttime) && Objects.equals(lunchendtime, that.lunchendtime) && Objects.equals(hourStartTime, that.hourStartTime) && Objects.equals(hourFinishTime, that.hourFinishTime);
    }

    @Override
    //ignore the collections
    public int hashCode() {
        return Objects.hash(id, user, monday, tuesday, wednesday, thursday, friday, saturday, sunday, startDate, finishDate, lunchstarttime, lunchendtime, hourStartTime, hourFinishTime);
    }

    public Availability(Set<Treatment> treatments, User user, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, Date startDate, Date finishDate, Time lunchstarttime, Time lunchendtime, Time hourStartTime, Time hourFinishTime) {
        this.treatments = treatments;
        this.user = user;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.lunchstarttime = lunchstarttime;
        this.lunchendtime = lunchendtime;
        this.hourStartTime = hourStartTime;
        this.hourFinishTime = hourFinishTime;
    }

//    public Availability(User user, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, Date startDate, Date finishDate, Time lunchstarttime, Time lunchendtime, Time hourStartTime, Time hourFinishTime) {
//        this.user = user;
//        this.monday = monday;
//        this.tuesday = tuesday;
//        this.wednesday = wednesday;
//        this.thursday = thursday;
//        this.friday = friday;
//        this.saturday = saturday;
//        this.sunday = sunday;
//        this.startDate = startDate;
//        this.finishDate = finishDate;
//        this.lunchstarttime = lunchstarttime;
//        this.lunchendtime = lunchendtime;
//        this.hourStartTime = hourStartTime;
//        this.hourFinishTime = hourFinishTime;
//    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "availability_treatment",
            joinColumns = @JoinColumn(name = "availability_id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id")
    )
    private Set<Treatment> treatments;
    //@ManyToMany(mappedBy = "availabilities", fetch = FetchType.EAGER)


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean monday;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean tuesday;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean wednesday;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean thursday;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean friday;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean saturday;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean sunday;

    @Column
    private Date startDate;
    @Column
    private Date finishDate;

    @Column
    private Time lunchstarttime;
    @Column
    private Time lunchendtime;
    @Column
    private Time hourStartTime;
    @Column
    private Time hourFinishTime;
}
