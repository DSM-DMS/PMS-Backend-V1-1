package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class StudentNotFoundException extends BusinessException {
    public StudentNotFoundException() {
        super(ErrorCode.STUDENT_NOT_FOUND);
    }
}
