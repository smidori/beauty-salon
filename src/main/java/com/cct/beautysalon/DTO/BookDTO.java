package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Availability;
import com.cct.beautysalon.models.Treatment;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private Availability availability;
    private LocalDateTime dateOfAgenda;
    private Treatment treatment;
    private Date dateBook;
    private Time startTimeBook;
    private Time finishTimeBook;
    private UserDTO clientUser;
}
