package com.dms.pms.security.oauth;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;

public interface OAuthProviderConnect {
    User getUserInfo(String token, AuthProvider type);
}
