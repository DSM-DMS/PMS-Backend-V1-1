package com.dms.pms.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AppleOAuthRequest {
    @ApiModelProperty(example = "암호화된 애플 아이디")
    @NotNull(message = "아이디는 필수 항목입니다.")
    private String id;

    @ApiModelProperty(example = "유저 이름")
    @NotNull(message = "이름은 필수 항목입니다.")
    private String name;
}
