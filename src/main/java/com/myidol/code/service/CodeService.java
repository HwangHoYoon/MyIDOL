package com.myidol.code.service;

import com.myidol.code.dto.CodeReq;
import com.myidol.code.dto.CodeRes;
import com.myidol.code.entity.Code;
import com.myidol.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
