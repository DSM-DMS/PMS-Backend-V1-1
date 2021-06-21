package com.dms.pms.security.oauth;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.exception.LoginFailedException;
import com.dms.pms.exception.OAuthConnectException;
import com.dms.pms.security.auth.RoleType;
import com.dms.pms.security.oauth.dto.ResponseData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OAuthProviderConnector implements OAuthProviderConnect {
    @Override
    public User getUserInfo(String token, AuthProvider type) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        switch (type) {
            case NAVER: {
                headers.add("Authorization", "Bearer " + token);
                Map<String, Object> data = requestProvider(type, restTemplate, headers);

                return User.builder()
                        .email((String) data.get("email"))
                        .name((String) data.get("nickname"))
                        .roleType(RoleType.USER)
                        .authProvider(type)
                        .build();
            }
            case FACEBOOK: {
                type.replaceAccessToken(token);
                Map<String, Object> data = requestProvider(type, restTemplate, headers);

                return User.builder()
                        .email((String) data.get("email"))
                        .name((String) data.get("name"))
                        .roleType(RoleType.USER)
                        .authProvider(type)
                        .build();
            }
            case KAKAO: {
                Map<String, Object> data = requestProvider(type, restTemplate, headers);

                return User.builder()
                        .email((String) data.get("email"))
                        .name((String) data.get("nickname"))
                        .roleType(RoleType.USER)
                        .authProvider(type)
                        .build();
            }
            default:
                throw new LoginFailedException();
        }
    }

    private Map<String, Object> requestProvider(AuthProvider type, RestTemplate restTemplate, HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseData<Map<String, Object>> responseData =
                restTemplate.exchange(
                        type.getProviderUri(),
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<ResponseData<Map<String, Object>>>() {
                        }
                ).getBody();

        int statusCode = Integer.parseInt(responseData.getResponseCode());

        if (statusCode == 401) {
            throw new LoginFailedException();
        } else if (statusCode != 200) {
            throw new OAuthConnectException();
        }

        return responseData.getResponse();
    }
}
