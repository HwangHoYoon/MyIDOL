package com.myidol.result.mgnt.repository;

import com.myidol.result.mgnt.entity.Result;
import com.myidol.result.mgnt.entity.ResultStatRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    // SELECT * FROM func_get_average_rank('hobby', '남성')
    @Query(value = "SELECT v_category AS category, v_stat AS stat, v_stat_rank AS statRank FROM func_get_average_rank(:category, :sex)", nativeQuery = true)
    List<ResultStatRank> getAverageRank(@Param("category") String category, @Param("sex") String sex);
}