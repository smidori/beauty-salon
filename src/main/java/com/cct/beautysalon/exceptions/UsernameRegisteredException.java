package com.cct.beautysalon.exceptions;

public class UsernameRegisteredException extends RuntimeException {

    public UsernameRegisteredException() {
        super("That username is taken. Try another");
    }

    public UsernameRegisteredException(String message) {
        super(message);
    }
    public UsernameRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameRegisteredException(Throwable cause) {
        super(cause);
    }
}
