package com.cct.beautysalon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AvailabilityDTO {
    private Long id;
    private Set<TreatmentSummaryDTO> treatments;
    private UserDTO user;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Time hourStartTime;
    private Time hourFinishTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilityDTO that = (AvailabilityDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(startDate, that.startDate) && Objects.equals(finishDate, that.finishDate) && Objects.equals(hourStartTime, that.hourStartTime) && Objects.equals(hourFinishTime, that.hourFinishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, startDate, finishDate, hourStartTime, hourFinishTime);
    }

}
