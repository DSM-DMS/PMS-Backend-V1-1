package com.dms.pms.service.auth;

import com.dms.pms.payload.request.*;
import com.dms.pms.payload.response.TokenResponse;
import com.dms.pms.utils.api.dto.apple.AppleToken;

public interface AuthService {
    TokenResponse login(LoginRequest request);
    void changePassword(PasswordChangeRequest request);
    TokenResponse oauthLogin(OAuthRequest request);
    AppleToken.Response appleOAuthLogin(AppleOAuthRequest request);
    TokenResponse signInApple(AppleSignRequest request);
}
