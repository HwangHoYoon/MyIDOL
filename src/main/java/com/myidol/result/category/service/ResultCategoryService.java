package com.myidol.result.category.service;

import com.myidol.result.category.entity.ResultCategory;
import com.myidol.result.category.repository.ResultCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultCategoryService {

    private final ResultCategoryRepository resultCategoryRepository;

    public void saveResultCategory(ResultCategory resultCategory) {
        resultCategoryRepository.save(resultCategory);
    }
}
