package com.dms.pms.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    BAD_REQUEST(400, "Bad Request: Invalid Parameter."),
    UNAUTHORIZED(401, "UnAuthorized: Please check your authentication"),
    PROVIDER_INVALID(401, "UnAuthorized: Provider is not matched."),
    PROVIDER_USER_INVALID(401, "UnAuthorized: Provider user is invalid."),
    INSUFFICIENT_USER_PERMISSIONS(403, "Forbidden: Insufficient user permissions."),
    LOCAL_PROVIDER_NOT_PERMITTED(403, "Forbidden: Local provider is not permitted."),
    USER_NOT_FOUND(404, "Not Found: User not found."),
    ITEM_NOT_FOUND(404, "Not Found: Point Item not found."),
    STUDENT_NOT_FOUND(404, "Not Found: Student not found."),
    USER_DUPLICATION(409, "Conflict: User already exists."),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error: Something went wrong.");

    private final int status;
    private final String message;
}
