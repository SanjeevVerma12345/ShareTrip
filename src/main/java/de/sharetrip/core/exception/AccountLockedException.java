package de.sharetrip.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class AccountLockedException extends BaseException {

    private static final String ERROR_MESSAGE = "User account is locked";

    public AccountLockedException() {
        super(ERROR_MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.LOCKED;
    }

}
