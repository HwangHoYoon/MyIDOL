package com.myidol.code.repository;

import com.myidol.code.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Integer> {
    Code findByCodeIgnoreCase(String code);

    List<Code> findByMainCategoryIgnoreCase(String mainCategory);

    List<Code> findByMainCategoryIgnoreCaseAndSubCategoryIgnoreCase(String mainCategory, String subCategory);
}