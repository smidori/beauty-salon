package com.cct.beautysalon.exceptions;

public class AuthenticationException extends RuntimeException {
    /**
     * Constructs a new AuthenticationException
     * @param message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs a new AuthenticationException
     * @param message
     * @param cause
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AuthenticationException
     * @param cause
     */
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
