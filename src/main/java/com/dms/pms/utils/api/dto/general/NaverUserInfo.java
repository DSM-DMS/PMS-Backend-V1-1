package com.dms.pms.utils.api.dto.general;

import com.dms.pms.utils.api.dto.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NaverUserInfo implements UserInfo {
    @JsonProperty("response")
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
        private String id;
        private String nickname;
        private String email;
    }
}
