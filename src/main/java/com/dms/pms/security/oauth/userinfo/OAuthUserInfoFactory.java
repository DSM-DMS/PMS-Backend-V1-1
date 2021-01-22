package com.dms.pms.security.oauth.userinfo;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.exception.OAuth2AuthenticationFailedException;

import java.util.Map;

public class OAuthUserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2AuthenticationFailedException();
        }
    }
}
