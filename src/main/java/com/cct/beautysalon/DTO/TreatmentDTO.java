package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.TreatmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreatmentDTO {
    private Long id;
    private String name;
    private double price;
    private TreatmentType type;
}
