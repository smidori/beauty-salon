package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.TreatmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreatmentSummaryDTO extends ItemDTO{
    private TreatmentType type;
    private int duration;

}
