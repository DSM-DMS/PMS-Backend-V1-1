package com.dms.pms.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    BAD_REQUEST(400, "Bad Request: Invalid Parameter."),
    USER_NOT_FOUND(404, "Not Found: User not found.");


    private final int status;
    private final String message;
}
