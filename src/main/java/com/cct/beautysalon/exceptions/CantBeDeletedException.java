package com.cct.beautysalon.exceptions;

public class CantBeDeletedException extends RuntimeException {

    //constructors
    public CantBeDeletedException() {
        super("It can't be deleted, due to existing references.");
    }
    public CantBeDeletedException(String message) {
        super(message);
    }
    public CantBeDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantBeDeletedException(Throwable cause) {
        super(cause);
    }
}
