package com.dms.pms.utils.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "naverClient", url = "")
public interface NaverClient {

}
