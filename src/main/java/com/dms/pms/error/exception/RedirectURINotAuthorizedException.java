package com.dms.pms.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RedirectURINotAuthorizedException extends RuntimeException {
    public RedirectURINotAuthorizedException(String message) {
        super(message);
    }

    public RedirectURINotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
