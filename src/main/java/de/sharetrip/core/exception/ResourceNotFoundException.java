package de.sharetrip.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Resource not found.";

    public ResourceNotFoundException() {
        super(ERROR_MESSAGE);
        log.error(ERROR_MESSAGE, this);
    }
}
