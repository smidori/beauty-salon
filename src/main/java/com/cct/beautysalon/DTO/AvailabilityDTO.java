package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Treatment;
import com.cct.beautysalon.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AvailabilityDTO {
    private Long id;
    private Set<Treatment> treatments;
    private UserDTO user;
//    private Boolean monday;
//    private Boolean tuesday;
//    private Boolean wednesday;
//    private Boolean thursday;
//    private Boolean friday;
//    private Boolean saturday;
//    private Boolean sunday;

    private Date startDate;
    private Date finishDate;

//    private Time lunchstarttime;
//    private Time lunchendtime;
    private Time hourStartTime;
    private Time hourFinishTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilityDTO that = (AvailabilityDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(treatments, that.treatments) && Objects.equals(user, that.user) && Objects.equals(startDate, that.startDate) && Objects.equals(finishDate, that.finishDate) && Objects.equals(hourStartTime, that.hourStartTime) && Objects.equals(hourFinishTime, that.hourFinishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, treatments, user, startDate, finishDate, hourStartTime, hourFinishTime);
    }
}
