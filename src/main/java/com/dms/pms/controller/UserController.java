package com.dms.pms.controller;

import com.dms.pms.payload.request.RegisterRequest;
import com.dms.pms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "유저", value = "유저 회원가입, 닉네임 변경 등을 할 수 있는 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "기본적인 회원가입 API", notes = "성공 시 상태 코드 201 반환.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원가입이 성공적으로 수행됨."),
            @ApiResponse(code = 400, message = "잘못된 요청. 요청 값 확인."),
            @ApiResponse(code = 409, message = "해당 정보로 회원가입 된 정보가 이미 있음.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void register(@RequestBody @Valid RegisterRequest request) {
        userService.register(request);
    }
}
