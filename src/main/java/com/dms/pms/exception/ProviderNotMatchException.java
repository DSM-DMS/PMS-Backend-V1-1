package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class ProviderNotMatchException extends BusinessException {
    public ProviderNotMatchException() {
        super(ErrorCode.PROVIDER_INVALID);
    }
}
