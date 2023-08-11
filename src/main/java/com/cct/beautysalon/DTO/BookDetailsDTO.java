package com.cct.beautysalon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDetailsDTO implements Comparable<BookDetailsDTO> {
    private long userId;
    private String userName;
    private LocalDate dateBook;
    private LocalTime startTimeBook;
    private LocalTime finishTimeBook;

    /**
     * override compareto
     *
     * @param other the object to be compared.
     * @return
     */
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
