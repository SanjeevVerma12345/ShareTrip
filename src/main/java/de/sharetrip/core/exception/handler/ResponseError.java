package de.sharetrip.core.exception.handler;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@Builder
public class ResponseError {

    private HttpStatus errorStatus;

    private String errorMessage;
}
