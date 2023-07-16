package com.cct.beautysalon.DTO;

import com.cct.beautysalon.models.Treatment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDetailsDTO implements Comparable<BookDetailsDTO>{
    private long userId;
    private String userName;
    //private Treatment treatment;
    private LocalDate dateBook;

    private LocalTime startTimeBook;

    private LocalTime finishTimeBook;

//    @Override
//    public int compareTo(BookDetailsDTO o) {
//        if(o.getStartTimeBook().after(this.startTimeBook)){
//            return 1;
//        }else if(o.getStartTimeBook().before(this.startTimeBook)){
//            return -1;
//        }else{
//            return 0;
//        }
//    }

    @Override
    public int compareTo(BookDetailsDTO other) {
        return this.userName.compareTo(other.userName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDetailsDTO that = (BookDetailsDTO) o;
        return userId == that.userId && Objects.equals(userName, that.userName) && Objects.equals(dateBook, that.dateBook) && Objects.equals(startTimeBook, that.startTimeBook) && Objects.equals(finishTimeBook, that.finishTimeBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, dateBook, startTimeBook, finishTimeBook);
    }
}
