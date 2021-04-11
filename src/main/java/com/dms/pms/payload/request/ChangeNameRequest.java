package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class ChangeNameRequest {
    @ApiModelProperty(example = "<바꿀 닉네임>")
    @NotNull
    private String name;
}
