package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AppleSignRequest {
    @ApiModelProperty(example = "Apple refresh token")
    @NotNull(message = "Access token must not be null.")
    private String refreshToken;
}
