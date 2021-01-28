package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class UserHasNotStudentException extends BusinessException {
    public UserHasNotStudentException() {
        super(ErrorCode.INSUFFICIENT_USER_PERMISSIONS);
    }
}
