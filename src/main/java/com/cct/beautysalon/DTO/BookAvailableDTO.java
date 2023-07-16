package com.cct.beautysalon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookAvailableDTO implements Comparable<BookAvailableDTO>{
    private LocalDate dateBook;

    private LocalTime startTimeBook;

    private LocalTime finishTimeBook;
    private Set<BookDetailsDTO> bookDetails;

    @Override
    public int compareTo(BookAvailableDTO o) {
        return o.startTimeBook.compareTo(startTimeBook);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAvailableDTO that = (BookAvailableDTO) o;
        return Objects.equals(dateBook, that.dateBook) && Objects.equals(startTimeBook, that.startTimeBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateBook, startTimeBook);
    }
}
