package com.dms.pms.utils.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class KakaoUserInfo {
    @JsonProperty("properties")
    private UserInfo userInfo;

    public static class UserInfo {
        private String email;
        private String nickname;
    }
}
