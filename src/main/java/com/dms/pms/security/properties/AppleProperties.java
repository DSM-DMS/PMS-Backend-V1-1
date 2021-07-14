package com.dms.pms.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("auth.oauth.apple")
public class AppleProperties {
    private String keyId;
    private String teamId;
}