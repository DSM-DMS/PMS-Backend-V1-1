package com.dms.pms.utils.api.dto.apple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AppleToken {
    @Getter
    @AllArgsConstructor @NoArgsConstructor
    @Builder
    public static class Request {
        private String code;
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class Response {
        private String accessToken;
        private String expiresIn;
        private String idToken;
        private String refreshToken;
        private String tokenType;
        private String error;
    }
}