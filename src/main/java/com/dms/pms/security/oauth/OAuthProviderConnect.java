package com.dms.pms.security.oauth;

import com.dms.pms.entity.pms.user.AuthProvider;

import java.util.Optional;

public interface OAuthProviderConnect {
    public <T> Optional<T> getUserInfo(String token, AuthProvider type);
}
