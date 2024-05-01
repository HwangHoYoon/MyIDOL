package com.myidol.code.controller;

import com.myidol.code.dto.CodeReq;
import com.myidol.code.dto.CodeRes;
import com.myidol.code.service.CodeService;
import com.myidol.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Code", description = "코드 API")
@RequestMapping("code")
public class CodeController {

    private final CodeService codeService;

    @Operation(summary = "코드 목록 조회(1Depth)", description = "코드 목록 조회(1Depth)", hidden = true)
    @GetMapping(path = "/getCodeList/{main_category}")
    public CommonResponse<List<CodeRes>> getCodeList(@PathVariable("main_category") String mainCategory) {
        CodeReq codeReq = new CodeReq();
        codeReq.setMainCategory(mainCategory);
        List<CodeRes> codeResList = codeService.selectOneDepthCodeList(codeReq);
        return new CommonResponse<>(String.valueOf(HttpStatus.OK.value()), codeResList, HttpStatus.OK.getReasonPhrase());
    }

    @Operation(summary = "코드 목록 조회(2Depth)", description = "코드 목록 조회(2Depth)", hidden = true)
    @GetMapping(path = "/getCodeList/{main_category}/{sub_category}")
    public CommonResponse<List<CodeRes>> getCodeList(@PathVariable("main_category") String mainCategory, @PathVariable("sub_category") String subCategory) {
        CodeReq codeReq = new CodeReq();
        codeReq.setMainCategory(mainCategory);
        codeReq.setSubCategory(subCategory);
        List<CodeRes> codeResList = codeService.selectTwoDepthCodeList(codeReq);
        return new CommonResponse<>(String.valueOf(HttpStatus.OK.value()), codeResList, HttpStatus.OK.getReasonPhrase());
    }
}
