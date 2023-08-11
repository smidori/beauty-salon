package com.cct.beautysalon.exceptions;

public class BadCredentialsException extends RuntimeException {
    /**
     * Constructs a new BadCredentialsException
     * @param message
     */
    public BadCredentialsException(String message) {
        super(message);
    }

    /**
     * Constructs a new BadCredentialsException
     * @param message
     * @param cause
     */
    public BadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new BadCredentialsException
     * @param cause
     */
    public BadCredentialsException(Throwable cause) {
        super(cause);
    }
}
