package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.TreatmentType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@SuperBuilder // Utiliza a anotação @SuperBuilder em vez de @Builder
//@EqualsAndHashCode(callSuper = true) // Habilita o EqualsAndHashCode para herdar os campos de ItemDTO
//@Builder
public class TreatmentSummaryDTO extends ItemDTO{
//    private Long id;
//    private String name;
//    private double price;
//    private String description;
    private TreatmentType type;
    private int duration;
    //private Set<AvailabilityDTO> availabilities;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TreatmentSummaryDTO that = (TreatmentSummaryDTO) o;
//        return Double.compare(that.price, price) == 0 && duration == that.duration && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(type, that.type);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, price, description, type, duration);
//    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TreatmentDTO that = (TreatmentDTO) o;
//        return Double.compare(that.price, price) == 0 && duration == that.duration && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(availabilities, that.availabilities);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, price, description, type, duration, availabilities);
//    }
}
