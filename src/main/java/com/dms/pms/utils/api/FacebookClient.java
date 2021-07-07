package com.dms.pms.utils.api;

import com.dms.pms.utils.api.dto.FacebookUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "facebookClient", url = "https://graph.facebook.com/")
public interface FacebookClient {
    @GetMapping("/me")
    FacebookUserInfoResponse getUserInfo(@RequestParam("fields") String fields, @RequestParam("access_token") String accessToken);
}
