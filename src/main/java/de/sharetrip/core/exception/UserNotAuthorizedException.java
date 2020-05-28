package de.sharetrip.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends BaseException {

    private static final String USER_NOT_AUTHORIZED_ERROR = "User is not authorized";

    public UserNotAuthorizedException() {
        super(USER_NOT_AUTHORIZED_ERROR);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}

