package com.dms.pms.service.auth;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.exception.LoginFailedException;
import com.dms.pms.exception.PasswordNotMatchesException;
import com.dms.pms.exception.ProviderNotMatchException;
import com.dms.pms.exception.ProviderUserInvalidException;
import com.dms.pms.payload.request.AppleOAuthRequest;
import com.dms.pms.payload.request.LoginRequest;
import com.dms.pms.payload.request.OAuthRequest;
import com.dms.pms.payload.request.PasswordChangeRequest;
import com.dms.pms.payload.response.TokenResponse;
import com.dms.pms.security.JwtTokenProvider;
import com.dms.pms.security.auth.AuthenticationFacade;
import com.dms.pms.security.oauth.OAuthProviderConnect;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationFacade authenticationFacade;
    private final OAuthProviderConnect oAuthProviderConnect;

    @Override
    public TokenResponse login(LoginRequest request) {
        return userRepository.findById(request.getEmail())
                .filter(user -> user.getAuthProvider().equals(AuthProvider.LOCAL))
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRoleType()))
                .map(TokenResponse::new)
                .orElseThrow(LoginFailedException::new);
    }

    @Override
    public void changePassword(PasswordChangeRequest request) {
        userRepository.findById(authenticationFacade.getUserEmail())
                .filter(user -> passwordEncoder.matches(request.getPrePassword(), user.getPassword()))
                .map(user -> {
                    if (!user.getAuthProvider().equals(AuthProvider.LOCAL))
                        throw new ProviderNotMatchException();
                    return user;
                })
                .map(user -> user.changePassword(passwordEncoder.encode(request.getPassword())))
                .map(userRepository::save)
                .orElseThrow(PasswordNotMatchesException::new);
    }

    @Override
    public TokenResponse oauthLogin(OAuthRequest request) {
        User requestUser = oAuthProviderConnect.getUserInfo(request.getToken(), request.getProvider());

        User user = userRepository.findById(requestUser.getEmail())
                .map(resultUser -> {
                    if (!resultUser.getAuthProvider().equals(requestUser.getAuthProvider())) {
                        throw new ProviderNotMatchException();
                    }
                    return resultUser;
                })
                .orElseGet(() -> userRepository.save(requestUser));

        return new TokenResponse(jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRoleType()));
    }

    @Override
    public TokenResponse appleOAuthLogin(AppleOAuthRequest request) {

    }
}
