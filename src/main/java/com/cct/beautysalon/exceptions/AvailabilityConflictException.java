package com.cct.beautysalon.exceptions;

public class AvailabilityConflictException extends RuntimeException {
    /**
     * Constructs a new AvailabilityConflictException
     * @param message
     */
    public AvailabilityConflictException(String message) {
        super(message);
    }

    /**
     * Constructs a new AvailabilityConflictException
     * @param message
     * @param cause
     */
    public AvailabilityConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AvailabilityConflictException
     * @param cause
     */
    public AvailabilityConflictException(Throwable cause) {
        super(cause);
    }
}
