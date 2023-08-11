package com.cct.beautysalon.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    //constructors
    public EmailAlreadyRegisteredException() {
        super("This email is already registered");
    }

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
    public EmailAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
