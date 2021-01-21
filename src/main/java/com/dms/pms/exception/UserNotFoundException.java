package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
