package com.myidol.code.service;

import com.myidol.code.dto.CodeReq;
import com.myidol.code.dto.CodeRes;
import com.myidol.code.entity.Code;
import com.myidol.code.repository.CodeRepository;
import com.myidol.common.exception.CommonErrorCode;
import com.myidol.common.exception.CommonException;
import com.myidol.common.response.CommonResponse;
import com.myidol.result.file.entity.ResultFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CodeService {

    private final CodeRepository codeRepository;

    public List<CodeRes> selectOneDepthCodeList(CodeReq codeReq) {

        List<CodeRes> codeResList = new ArrayList<>();
        List<Code> codeList = codeRepository.findByMainCategoryIgnoreCase(codeReq.getMainCategory());

        codeList.forEach(code -> {
            codeResList.add(getCodeRes(code));
        });
        return codeResList;
    }

    public List<CodeRes> selectTwoDepthCodeList(CodeReq codeReq) {
        List<CodeRes> codeResList = new ArrayList<>();
        List<Code> codeList = codeRepository.findByMainCategoryIgnoreCaseAndSubCategoryIgnoreCase(codeReq.getMainCategory(), codeReq.getSubCategory());
        codeList.forEach(code -> {
            codeResList.add(getCodeRes(code));
        });
        return codeResList;
    }

    private CodeRes getCodeRes(Code code) {
        CodeRes codeRes = new CodeRes();
        codeRes.setId(code.getId());
        codeRes.setCode(code.getCode());
        codeRes.setName(code.getName());
        codeRes.setDescription(code.getDescription());
        codeRes.setPicture(code.getPicture());
        return codeRes;
    }
    public ResponseEntity<Resource> loadImage(String id) {
        if (StringUtils.isBlank(id)) {
            log.error("이미지 조회 정보가 잘못되었습니다. {}", id);
            throw new CommonException(CommonErrorCode.INVALID_ID);
        }
        Integer resultId = Integer.parseInt(id);
        Code code = codeRepository.findById(resultId).orElseThrow(() -> new CommonException(CommonErrorCode.INVALID_ID));
        String picture = code.getPicture();
        Resource resource = new FileSystemResource(picture);
        if (!resource.exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(picture);
            header.add("Content-type", Files.probeContentType(filePath));
        } catch(IOException e) {
            log.error("이미지를 불러오는대 실패하였습니다. {}", picture);
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    public ResponseEntity<Resource> getIdolImage(String codeName) {
        Code code = codeRepository.findByMainCategoryIgnoreCaseAndNameIgnoreCase("artist",  codeName);
        if (code != null) {
            return loadImage(String.valueOf(code.getId()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
