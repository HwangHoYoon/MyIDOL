package com.myidol.result.file.service;

import com.myidol.result.category.entity.ResultCategory;
import com.myidol.result.file.entity.ResultFile;
import com.myidol.result.file.repository.ResultFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultFileService {
    private final ResultFileRepository resultFileRepository;

    public void saveResultFile(ResultFile resultFile) {
        resultFileRepository.save(resultFile);
    }

    public ResultFile selectResultFileByResultId(Integer resultId) {
        return resultFileRepository.findByResult_Id(resultId);
    }
}
