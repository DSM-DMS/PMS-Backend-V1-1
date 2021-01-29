package com.dms.pms.payload.request;

import com.dms.pms.entity.pms.outing.OutingType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AddOutingRequest {
    @ApiModelProperty(example = "<학생 학번>")
    @NotNull
    private Integer number;

    @ApiModelProperty(example = "<사유>")
    @NotNull
    private String reason;

    @ApiModelProperty(example = "<장소>")
    @NotNull
    private String place;

    @NotNull
    @ApiModelProperty(example = "<외출 종류>", value = "NORMAL: 일반 외출, DISEASE: 질병 외출. (대소문자 무시)")
    private OutingType type;

    public void setType(String type) {
        this.type = OutingType.valueOf(type.toUpperCase());
    }

    public OutingType getType() {
        return type;
    }
}