package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class PasswordNotMatchesException extends BusinessException {
    public PasswordNotMatchesException() {
        super("Password is not matched!!", ErrorCode.UNAUTHORIZED);
    }
}
