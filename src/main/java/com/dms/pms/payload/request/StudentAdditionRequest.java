package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor @NoArgsConstructor @Getter
public class StudentAdditionRequest {
    @ApiModelProperty(example = "<studentNumber>", value = "학생 인증번호")
    @NotNull
    private Integer number;
}
