package com.dms.pms.payload.request;

import com.dms.pms.entity.pms.user.AuthProvider;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthRequest {
    @ApiModelProperty(example = "<OAuth 토큰>")
    @NotNull
    private String token;

    @ApiModelProperty(example = "<OAuth 공급자> 들어갈 수 있는 값들: [NAVER, FACEBOOK, APPLE, KAKAO] (대소문자 무시)")
    @NotNull
    private AuthProvider provider;

    public void setProvider(String type) {
        this.provider = AuthProvider.valueOf(type.toUpperCase());
    }
}
