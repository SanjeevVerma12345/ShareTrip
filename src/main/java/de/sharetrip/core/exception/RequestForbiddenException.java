package de.sharetrip.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RequestForbiddenException extends RuntimeException {

    public RequestForbiddenException(final String message) {
        super(message);
    }
}
