package com.dms.pms.service.auth;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.exception.LoginFailedException;
import com.dms.pms.exception.PasswordNotMatchesException;
import com.dms.pms.exception.ProviderNotMatchException;
import com.dms.pms.exception.ProviderUserInvalidException;
import com.dms.pms.payload.request.*;
import com.dms.pms.payload.response.TokenResponse;
import com.dms.pms.security.JwtTokenProvider;
import com.dms.pms.security.auth.AuthenticationFacade;
import com.dms.pms.security.auth.RoleType;
import com.dms.pms.security.oauth.OAuthProviderConnect;
import com.dms.pms.security.properties.AuthProperties;
import com.dms.pms.utils.api.client.AppleClient;
import com.dms.pms.utils.api.dto.apple.AppleToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationFacade authenticationFacade;
    private final OAuthProviderConnect oAuthProviderConnect;
    private final AppleClient appleClient;
    private final AuthProperties.Oauth.Apple appleProperties;

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
    @Transactional
    public AppleToken.Response appleOAuthLogin(AppleOAuthRequest request) {
        Claims claims = jwtTokenProvider.getClaimsByAppleIdentityToken(request.getIdentityToken());

        return appleClient.getToken(
                AppleToken.Request.builder()
                        .code(request.getAuthorizationCode())
                        .clientId("com.dsm.pms-v2")
                        .clientSecret(jwtTokenProvider.makeAppleSecret())
                        .grantType("authorization_code")
                        .build()
        );
    }

    @Override
    public TokenResponse signInApple(AppleSignRequest request) {
        Claims claims = jwtTokenProvider.getClaimsByAppleIdentityToken(request.getRefreshToken());
        String id = claims.getSubject();

        return userRepository.findById(id)
                .filter(resultUser -> resultUser.getAuthProvider().equals(AuthProvider.APPLE))
                .map(User::getEmail)
                .map(email -> jwtTokenProvider.generateAccessToken(email, RoleType.USER))
                .map(TokenResponse::new)
                .orElseGet(() -> {
                    User user = userRepository.save(
                        User.builder()
                                .email(id)
                                .name("이름을 변경해주세요")
                                .authProvider(AuthProvider.APPLE)
                                .roleType(RoleType.USER)
                                .build()
                    );

                    String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), RoleType.USER);

                    return new TokenResponse(accessToken);
                });
    }
}
