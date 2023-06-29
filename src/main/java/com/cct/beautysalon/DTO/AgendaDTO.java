package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Availability;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendaDTO {
    private Long id;
    private Availability availability;
    private LocalDateTime dateOfAgenda;
}
