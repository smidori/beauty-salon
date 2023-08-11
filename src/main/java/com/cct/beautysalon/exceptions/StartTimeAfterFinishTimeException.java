package com.cct.beautysalon.exceptions;

public class StartTimeAfterFinishTimeException extends RuntimeException {
    //constructors
    public StartTimeAfterFinishTimeException() {
        super("Finish Time must be greater than or equal to Start Time");
    }

    public StartTimeAfterFinishTimeException(String message) {
        super(message);
    }
    public StartTimeAfterFinishTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public StartTimeAfterFinishTimeException(Throwable cause) {
        super(cause);
    }
}
