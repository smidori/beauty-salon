package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.TreatmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreatmentDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private TreatmentType type;
    private int duration;
    private Set<Availability> availabilities;
}
