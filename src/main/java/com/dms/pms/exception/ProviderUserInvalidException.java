package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class ProviderUserInvalidException extends BusinessException {
    public ProviderUserInvalidException() {
        super(ErrorCode.PROVIDER_USER_NOT_FOUND);
    }
}
