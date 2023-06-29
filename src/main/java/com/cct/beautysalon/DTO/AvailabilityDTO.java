package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailabilityDTO {
    private Long id;
    //private Treatment treatment;
    private Set<Treatment> treatments;
    private User user;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;

    private Date startDate;
    private Date finishDate;

    private Time lunchstarttime;
    private Time lunchendtime;
    private Time hourStartTime;
    private Time hourFinishTime;
}
