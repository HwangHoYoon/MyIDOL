package com.myidol.result.file.repository;

import com.myidol.result.file.entity.ResultFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultFileRepository extends JpaRepository<ResultFile, Integer> {
    ResultFile findByResult_Id(Integer id);
}