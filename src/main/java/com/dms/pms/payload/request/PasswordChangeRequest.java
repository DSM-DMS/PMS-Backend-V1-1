package com.dms.pms.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter @AllArgsConstructor @NoArgsConstructor
public class PasswordChangeRequest {
    @JsonProperty("pre-password")
    @ApiModelProperty(example = "<이전 비밀번호>")
    @NotNull
    private String prePassword;

    @JsonProperty("password")
    @ApiModelProperty(example = "<바꿀 비밃번호>")
    @NotNull
    private String password;
}
