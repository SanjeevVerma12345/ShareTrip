package de.sharetrip.core.exception;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@Builder
public class ResponseError {

    private HttpStatus errorStatus;

    private String errorMessage;
}
