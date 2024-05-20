package com.myidol.result.mgnt.controller;

import com.myidol.common.response.CommonResponse;
import com.myidol.result.mgnt.dto.ResultReq;
import com.myidol.result.mgnt.dto.ResultRes;
import com.myidol.result.mgnt.dto.ResultStatRankRes;
import com.myidol.result.mgnt.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Result", description = "결과 API")
@RequestMapping("result")
public class ResultController {

    private final ResultService resultService;

    @Operation(summary = "결과 등록", description = "결과 등록")
    @PostMapping(path = "/saveResult")
    public CommonResponse<ResultRes> saveResult(@RequestBody ResultReq resultReq) {
        ResultRes resultRes = resultService.saveResult(resultReq);
        return new CommonResponse<>(String.valueOf(HttpStatus.OK.value()), resultRes, HttpStatus.OK.getReasonPhrase());
    }

    @Operation(summary = "결과 조회", description = "결과 조회")
    @GetMapping(path = "/getResult/{id}")
    public CommonResponse<ResultRes> getResult(@PathVariable("id") String id) {
        ResultRes resultRes = resultService.selectResult(id);
        return new CommonResponse<>(String.valueOf(HttpStatus.OK.value()), resultRes, HttpStatus.OK.getReasonPhrase());
    }

    @Operation(summary = "랭킹 조회", description = "랭킹 조회")
    @GetMapping(path = "/getResultStatRank/{sex}")
    public CommonResponse<ResultStatRankRes> getResultStatRank(@PathVariable("sex") String sex) {
        ResultStatRankRes resultStatRankRes = resultService.selectResultStatRank(sex);
        return new CommonResponse<>(String.valueOf(HttpStatus.OK.value()), resultStatRankRes, HttpStatus.OK.getReasonPhrase());
    }

    @GetMapping("/image/{id}")
    @Operation(summary = "이미지 조회", description = "이미지 조회")
    public ResponseEntity<Resource>  loadImage(@PathVariable("id") String id) {
        return resultService.loadImage(id);
    }

    @Operation(summary = "총 건수 조회", description = "총 건수 조회")
    @GetMapping(path = "/getResultCount")
    public CommonResponse<Long> getResultCount() {
        Long count = resultService.selectResultCount();
        return new CommonResponse<>(String.valueOf(HttpStatus.OK.value()), count, HttpStatus.OK.getReasonPhrase());
    }
}
