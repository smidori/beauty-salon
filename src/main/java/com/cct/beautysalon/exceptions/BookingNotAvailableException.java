package com.cct.beautysalon.exceptions;

public class BookingNotAvailableException extends RuntimeException {

    public BookingNotAvailableException() {
        super("Sorry,this booking isn't available anymore, please choose another day, time and or professional");
    }

    public BookingNotAvailableException(String message) {
        super(message);
    }
    public BookingNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingNotAvailableException(Throwable cause) {
        super(cause);
    }
}
