package com.dms.pms.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter
public class StudentInformationResponse {
    @ApiModelProperty(example = "<상점 수치>", value = "상점 수치")
    @JsonProperty("bonus-point")
    private int bonusPoint;

    @ApiModelProperty(example = "<벌점 수치>", value = "벌점 수치")
    @JsonProperty("minus-point")
    private int minusPoint;

    @ApiModelProperty(example = "<잔류 상태>", value = "1: 금요귀가, 2: 토요귀가, 3: 토요귀사, 4: 잔류")
    @JsonProperty("stay-status")
    private int stay;

    @ApiModelProperty(example = "<주말급식 신청 여부>", value = "true: 신청, false: 미신청")
    @JsonProperty("meal-applied")
    private boolean isMealApplied;
}
