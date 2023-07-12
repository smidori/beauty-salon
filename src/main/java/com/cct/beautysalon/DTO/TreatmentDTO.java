package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.TreatmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TreatmentDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private TreatmentType type;
    private int duration;
    private Set<AvailabilityDTO> availabilities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentDTO that = (TreatmentDTO) o;
        return Double.compare(that.price, price) == 0 && duration == that.duration && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(availabilities, that.availabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, type, duration, availabilities);
    }
}
