package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class LoginFailedException extends BusinessException {
    public LoginFailedException() {
        super("The login information is not valid", ErrorCode.UNAUTHORIZED);
    }
}
