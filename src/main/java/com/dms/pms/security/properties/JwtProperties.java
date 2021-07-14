package com.dms.pms.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("auth.jwt")
public class JwtProperties {
    private String secret;
    private Long accessExp;
    private String header;
    private String prefix;
}
