package com.dms.pms.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenResponse {
    @JsonProperty("access-token")
    private String accessToken;
}
