package com.dms.pms.utils.api.client;

import com.dms.pms.utils.api.dto.apple.ApplePublicKeyResponse;
import com.dms.pms.utils.api.dto.apple.AppleToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleClient {
    @GetMapping("/keys")
    ApplePublicKeyResponse getApplePublicKey();

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AppleToken.Response getToken(AppleToken.Request request);
}
