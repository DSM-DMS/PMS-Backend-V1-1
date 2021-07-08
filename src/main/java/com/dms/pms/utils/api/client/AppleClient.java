package com.dms.pms.utils.api.client;

import com.dms.pms.utils.api.dto.ApplePublicKeyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleClient {
    @GetMapping("/keys")
    ApplePublicKeyResponse getApplePublicKey();
}
