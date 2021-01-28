package com.dms.pms.controller;

import com.dms.pms.payload.request.LoginRequest;
import com.dms.pms.payload.response.TokenResponse;
import com.dms.pms.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "인증", value = "인증 및 토큰 발급을 할 수 있는 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "기본적인 로그인 API", notes = "성공 시 access token 반환.", response = TokenResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인이 성공적으로 수행됨."),
            @ApiResponse(code = 400, message = "잘못된 요청. 요청 값 확인."),
            @ApiResponse(code = 401, message = "로그인 정보가 일치하지 않음.")
    })
    @PostMapping
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
