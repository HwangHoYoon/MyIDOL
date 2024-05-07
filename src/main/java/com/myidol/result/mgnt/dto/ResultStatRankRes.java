package com.myidol.result.mgnt.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultStatRankRes {
    private List<ResultStatRankData> mbtiStatRankData;
    private List<ResultStatRankData> looklikeStatRankData;
    private List<ResultStatRankData> heightStatRankData;
    private List<ResultStatRankData> eyeshapeStatRankData;
    private List<ResultStatRankData> faceshapeStatRankData;
    private List<ResultStatRankData> fashionStatRankData;
    private List<ResultStatRankData> hobbyStatRankData;
}
