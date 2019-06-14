package com.dkasiian.controllers.exceptions;

public class DuplicateSubmitException extends RuntimeException {
    public DuplicateSubmitException() { }

    public DuplicateSubmitException(String message) {
        super(message);
    }

    public DuplicateSubmitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateSubmitException(Throwable cause) {
        super(cause);
    }

    public DuplicateSubmitException(String message,
                                    Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
