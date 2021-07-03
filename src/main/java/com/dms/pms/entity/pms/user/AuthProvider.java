package com.dms.pms.entity.pms.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {
    FACEBOOK("https://graph.facebook.com/me?fields=email,name/&access_token={access_token}"),
    APPLE(""),
    KAKAO(""),
    NAVER(""),
    LOCAL(null);

    private String providerUri;

    public AuthProvider replaceAccessToken(String token) {
        this.providerUri =  providerUri.replace("{access_token}", token);
        return this;
    }
}
