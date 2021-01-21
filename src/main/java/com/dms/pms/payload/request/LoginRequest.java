package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class LoginRequest {
    @ApiModelProperty(example = "<email>")
    @Email @NotNull
    private String email;

    @ApiModelProperty(example = "<password>")
    @NotNull
    private String password;
}
