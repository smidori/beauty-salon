package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Treatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookSearchParamsDTO {
    private UserDTO user;
    private TreatmentDTO treatment;
    private Date dateBook;
    //private Time startTimeBook;
    //private Time finishTimeBook;
}
