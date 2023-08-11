package com.cct.beautysalon.exceptions;

public class NotFoundException extends RuntimeException {
    //constructors
    public NotFoundException() { super("Register not found."); }
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
