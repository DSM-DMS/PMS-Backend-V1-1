package com.dms.pms.service.auth;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.exception.LoginFailedException;
import com.dms.pms.payload.request.LoginRequest;
import com.dms.pms.payload.response.TokenResponse;
import com.dms.pms.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponse login(LoginRequest request) {
        return userRepository.findById(request.getEmail())
                .filter(user -> user.getAuthProvider().equals(AuthProvider.local))
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRoleType()))
                .map(TokenResponse::new)
                .orElseThrow(LoginFailedException::new);
    }
}
