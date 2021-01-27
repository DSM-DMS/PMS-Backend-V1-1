package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class OAuth2AuthenticationFailedException extends BusinessException {
    public OAuth2AuthenticationFailedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
