package com.dms.pms.exception;

import com.dms.pms.error.exception.BusinessException;
import com.dms.pms.error.exception.ErrorCode;

public class ItemNotFoundException extends BusinessException {
    public ItemNotFoundException() {
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
