package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class StudentDeleteRequest {
    @ApiModelProperty(example = "<학번>")
    private int number;
}
