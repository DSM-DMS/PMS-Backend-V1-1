package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AppleOAuthRequest {
    @ApiModelProperty(example = "Apple identity token")
    @NotNull(message = "identityToken은 필수 항목입니다.")
    private String identityToken;

    @ApiModelProperty(example = "Apple authorization code")
    @Length(min = 6, max = 6, message = "Authorization code must be 6 digits.")
    @NotNull(message = "Authorization code must not be null.")
    private String authorizationCode;
}