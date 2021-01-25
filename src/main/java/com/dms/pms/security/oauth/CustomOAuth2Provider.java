package com.dms.pms.security.oauth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOAuth2Provider {
    NAVER {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, "{baseUrl}/{action}/oauth2/code/{registrationId}").scope("profile")
                    .scope("name", "email")
                    .redirectUri("http://localhost:8080/oauth2/callback/naver")
                    .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .tokenUri("https://nid.naver.com/oauth2.0/token")
                    .userInfoUri("https://openapi.naver.com/v1/nid/me")
                    .userNameAttributeName("response")
                    .clientName("Naver");

            return builder;
        }
    };

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration
                .withRegistrationId(registrationId)
                .clientAuthenticationMethod(method);

        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}
