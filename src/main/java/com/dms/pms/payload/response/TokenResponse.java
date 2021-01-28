package com.dms.pms.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenResponse {
    @ApiModelProperty(example = "<access-token>", value = "access token")
    @JsonProperty("access-token")
    private String accessToken;
}
