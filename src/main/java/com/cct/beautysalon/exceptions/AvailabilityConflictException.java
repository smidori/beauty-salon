package com.cct.beautysalon.exceptions;

public class AvailabilityConflictException extends RuntimeException {
    public AvailabilityConflictException(String message) {
        super(message);
    }

    public AvailabilityConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvailabilityConflictException(Throwable cause) {
        super(cause);
    }
}
