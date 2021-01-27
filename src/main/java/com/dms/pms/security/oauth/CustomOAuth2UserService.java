package com.dms.pms.security.oauth;

import com.dms.pms.entity.pms.user.Parent;
import com.dms.pms.entity.pms.user.ParentRepository;
import com.dms.pms.exception.OAuth2AuthenticationFailedException;
import com.dms.pms.security.auth.AuthDetails;
import com.dms.pms.security.auth.RoleType;
import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.security.oauth.userinfo.OAuth2UserInfo;
import com.dms.pms.security.oauth.userinfo.OAuthUserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final ParentRepository parentRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuthUserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(oAuth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationFailedException();
        }

        Optional<Parent> parentOptional = parentRepository.findById(oAuth2UserInfo.getEmail());
        Parent parent;
        if(parentOptional.isPresent()) {
            parent = parentOptional.get();
            if(!parent.getAuthProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationFailedException();
            }
            parent = updateExistingUser(parent, oAuth2UserInfo);
        } else {
            parent = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return new AuthDetails(parent, oAuth2User.getAttributes());
    }

    private Parent registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        return parentRepository.save(
                Parent.builder()
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .authProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .roleType(RoleType.USER) // 소셜 로그인 유저는 항상 USER 권한
                .build()
        );
    }

    private Parent updateExistingUser(Parent existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        return parentRepository.save(existingUser);
    }

}