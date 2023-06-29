package com.cct.beautysalon.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="availability")
public class Availability{

//    public Availability(Treatment treatment, User user, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, Date startDate, Date finishDate, Time lunchstarttime, Time lunchendtime, Time hourStartTime, Time hourFinishTime) {
//        this.treatment = treatment;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "treatment_id")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "availability_treatment",
            joinColumns = @JoinColumn(name = "availability_id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id")
    )
    private Set<Treatment> treatments;

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
