package com.dms.pms.utils.api.client;

import com.dms.pms.utils.api.dto.general.NaverUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "naverClient", url = "https://openapi.naver.com/v1/nid/")
public interface NaverClient {
    @GetMapping("/me")
    NaverUserInfo getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
