package com.dms.pms.service.auth;

import com.dms.pms.payload.request.LoginRequest;
import com.dms.pms.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest request);
}
