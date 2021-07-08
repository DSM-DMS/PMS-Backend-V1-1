package com.dms.pms.utils.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaverUserInfo {
    @JsonProperty("response")
    private UserInfo userInfo;

    public static class UserInfo {
        private String id;
        private String nickname;
        private String email;
    }
}
