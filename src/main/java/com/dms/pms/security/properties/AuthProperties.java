package com.dms.pms.security.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("auth")
public class AuthProperties {

    private Jwt jwt;
    private Oauth oauth;

    @Getter
    public static class Jwt {
        private String secret;
        private Long accessExp;
        private String header;
        private String prefix;
    }

    @Getter
    public static class Oauth {

        private Apple apple;

        @Getter
        public static class Apple {
            private String keyId;
            private String teamId;
        }
    }
}
