package de.sharetrip.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<ResponseError> exceptionHandler(final Exception exception) {
        log.error("This should not happen, but will try to investigate the exception");
        final ResponseStatus responseStatus = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        final ResponseError build = ResponseError
                .builder()
                .errorMessage(exception.getMessage())
                .errorStatus(responseStatus.value())
                .build();
        return new ResponseEntity<>(build, build.getErrorStatus());
    }

}
