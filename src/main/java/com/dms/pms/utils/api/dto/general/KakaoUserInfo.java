package com.dms.pms.utils.api.dto.general;

import com.dms.pms.utils.api.dto.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class KakaoUserInfo implements UserInfo {
    @JsonProperty("properties")
    private UserInfo userInfo;

    @Override
    public String getUserName() {
        return userInfo.nickname;
    }

    @Override
    public String getEmail() {
        return userInfo.email;
    }

    public static class UserInfo {
        private String email;
        private String nickname;
    }
}
