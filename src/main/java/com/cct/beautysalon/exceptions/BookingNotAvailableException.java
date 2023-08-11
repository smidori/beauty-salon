package com.cct.beautysalon.exceptions;

public class BookingNotAvailableException extends RuntimeException {
    /**
     * Construct
     */
    public BookingNotAvailableException() {
        super("Sorry,this booking isn't available anymore, please choose another day, time and or professional");
    }

    /**
     * Construct
     * @param message
     */
    public BookingNotAvailableException(String message) {
        super(message);
    }

    /**
     * Construct
     * @param message
     * @param cause
     */
    public BookingNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct
     * @param cause
     */
    public BookingNotAvailableException(Throwable cause) {
        super(cause);
    }
}
