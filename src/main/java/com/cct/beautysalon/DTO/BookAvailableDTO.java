package com.cct.beautysalon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookAvailableDTO implements Comparable<BookAvailableDTO>{
    private Date dateBook;
    private Time startTimeBook;
    private Time finishTimeBook;
    private Set<BookDetailsDTO> bookDetails;

    @Override
    public int compareTo(BookAvailableDTO o) {
        if(o.getStartTimeBook().after(this.startTimeBook)){
            return 1;
        }else if(o.getStartTimeBook().before(this.startTimeBook)){
            return -1;
        }else{
            return 0;
        }
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
