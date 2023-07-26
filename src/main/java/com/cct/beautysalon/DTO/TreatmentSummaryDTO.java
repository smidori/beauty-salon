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
    private TreatmentType type;
    private int duration;
}
