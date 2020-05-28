package de.sharetrip.core.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends Exception {

    public BaseException(final String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
 
}
