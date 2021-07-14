package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class FailedConnectAppleException extends BusinessException {
    public FailedConnectAppleException() {
        super(ErrorCode.FAILED_TO_CONNECT_APPLE_SERVER);
    }
}
