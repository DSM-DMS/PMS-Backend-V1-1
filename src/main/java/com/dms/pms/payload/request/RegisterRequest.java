package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    @ApiModelProperty(example = "<id>")
    private String id;

    @ApiModelProperty(example = "<password>")
    private String password;

    @ApiModelProperty(example = "<name>")
    private String name;
}
