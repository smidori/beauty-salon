package com.cct.beautysalon.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookSearchParamsDTO {
    private UserDTO user;
    private TreatmentSummaryDTO treatment;

    private LocalDate dateBook;

    //private LocalTime startTimeBook;

    //private LocalTime finishTimeBook;

}
