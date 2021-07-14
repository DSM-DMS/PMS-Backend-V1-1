package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class OAuthConnectException extends BusinessException {
    public OAuthConnectException() {
        super("Server failed to connect OAuth provider server.", ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
