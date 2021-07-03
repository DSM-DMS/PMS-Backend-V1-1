package com.dms.pms.service.auth;

import com.dms.pms.payload.request.AppleOAuthRequest;
import com.dms.pms.payload.request.LoginRequest;
import com.dms.pms.payload.request.OAuthRequest;
import com.dms.pms.payload.request.PasswordChangeRequest;
import com.dms.pms.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest request);
    void changePassword(PasswordChangeRequest request);
    TokenResponse oauthLogin(OAuthRequest request);
    public TokenResponse appleOAuthLogin(AppleOAuthRequest request);
}
