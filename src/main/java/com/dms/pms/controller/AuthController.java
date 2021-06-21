package com.dms.pms.controller;

import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.exception.ProviderNotPermittedException;
import com.dms.pms.payload.request.LoginRequest;
import com.dms.pms.payload.request.OAuthRequest;
import com.dms.pms.payload.request.PasswordChangeRequest;
import com.dms.pms.payload.response.TokenResponse;
import com.dms.pms.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @ApiOperation(value = "비밀번호 변경 API", notes = "성공 시 201 반환.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "비밀번호 변경이 성공적으로 수행됨."),
            @ApiResponse(code = 400, message = "잘못된 요청. 요청 값 확인."),
            @ApiResponse(code = 401, message = "비밀번호가 일치하지 않음 or 인증 정보가 유효하지 않음.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/password")
    public void changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        authService.changePassword(request);
    }

    @ApiOperation(value = "OAuth 정보 등록 API", notes = "성공 시 access token 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OAuth를 통하여 회원가입 혹은 로그인이 정상적으로 수행되었고 토큰을 성공적으로 반환함."),
            @ApiResponse(code = 400, message = "잘못된 요청. 요청 값 확인."),
            @ApiResponse(code = 401, message = "OAuth token과 현재 있는 계정 정보의 oauth 제공자가 일치하지 않음.")
    })
    @PostMapping("/oauth")
    public TokenResponse oauthLogin(@RequestBody @Valid OAuthRequest request) {
        if (request.getProvider().equals(AuthProvider.LOCAL))
            throw new ProviderNotPermittedException();

        return authService.oauthLogin(request);
    }
}
