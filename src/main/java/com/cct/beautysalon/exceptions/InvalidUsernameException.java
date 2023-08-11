package com.cct.beautysalon.exceptions;

public class InvalidUsernameException extends RuntimeException {
    //constructors
    public InvalidUsernameException() {
        super("Invalid username");
    }

    public InvalidUsernameException(String message) {
        super(message);
    }

    public InvalidUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUsernameException(Throwable cause) {
        super(cause);
    }
}
