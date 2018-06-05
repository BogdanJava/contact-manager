package com.itechart.contactmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RestrictionException extends RuntimeException {

    public RestrictionException(String message) {
        super(message);
    }

    public RestrictionException(String message, Throwable cause) {
        super(message, cause);
    }
}
