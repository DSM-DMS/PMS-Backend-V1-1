package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class ProviderNotPermittedException extends BusinessException {
    public ProviderNotPermittedException() {
        super(ErrorCode.LOCAL_PROVIDER_NOT_PERMITTED);
    }
}
