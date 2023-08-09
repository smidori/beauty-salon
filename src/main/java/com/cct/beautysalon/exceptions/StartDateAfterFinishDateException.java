package com.cct.beautysalon.exceptions;

public class StartDateAfterFinishDateException extends RuntimeException {

    public StartDateAfterFinishDateException() {
        super("Finish Date must be greater than or equal to Start Date");
    }

    public StartDateAfterFinishDateException(String message) {
        super(message);
    }
    public StartDateAfterFinishDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public StartDateAfterFinishDateException(Throwable cause) {
        super(cause);
    }
}
