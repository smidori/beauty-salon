package com.cct.beautysalon.DTO;

import com.cct.beautysalon.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookFilterParamsDTO {
    private Long workerId;
    private Long clientId;
    private LocalDate dateBook;
    private BookStatus bookStatus;
}
