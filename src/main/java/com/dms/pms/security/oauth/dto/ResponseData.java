package com.dms.pms.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {
    private String responseCode;
    private T response;
    private String message;
}
