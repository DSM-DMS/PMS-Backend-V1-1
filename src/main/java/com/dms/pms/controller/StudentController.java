package com.dms.pms.controller;

import com.dms.pms.payload.request.AddOutingRequest;
import com.dms.pms.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "학생", value = "학생 관리 API, 어드민 전용")
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @ApiOperation(value = "학생 외출 등록 API", notes = "성공 시 상태 코드 201 반환.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "요청이 성공적으로 수행됨."),
            @ApiResponse(code = 400, message = "잘못된 요청, 요청 값 확인"),
            @ApiResponse(code = 401, message = "인증 정보가 유효하지 않음."),
            @ApiResponse(code = 403, message = "접근 권한 없음.(권한이 유저일때 발생)"),
            @ApiResponse(code = 404, message = "해당하는 학생이 존재하지 않음.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/outing")
    public void addOuting(@RequestBody @Valid AddOutingRequest request) {
        studentService.addOuting(request);
    }
}
