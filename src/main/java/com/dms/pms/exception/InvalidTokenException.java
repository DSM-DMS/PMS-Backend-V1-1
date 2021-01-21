package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super("Invalid Token!!", ErrorCode.UNAUTHORIZED);
    }
}
