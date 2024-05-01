package com.myidol.result.service;

import com.myidol.common.exception.CommonErrorCode;
import com.myidol.common.exception.CommonException;
import com.myidol.result.dto.ResultReq;
import com.myidol.result.dto.ResultRes;
import com.myidol.result.dto.ResultStatRankData;
import com.myidol.result.dto.ResultStatRankRes;
import com.myidol.result.entity.Result;
import com.myidol.result.entity.ResultStatRank;
import com.myidol.result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    @Value("${file.path}")
    private String uploadPath;

    @Value("${domain}")
    private String domain;

    public ResultRes saveResult(ResultReq resultReq) {
        ResultRes resultRes = new ResultRes();

        Result result = Result.builder()
                .age(resultReq.getAge())
                .sex(resultReq.getSex())
                .mbti(resultReq.getMbti())
                .looklike(resultReq.getLookLike())
                .height(resultReq.getHeight())
                .eyeshape(resultReq.getEyeShape())
                .faceshape(resultReq.getFaceShape())
                .fashion(resultReq.getFashion())
                .interest(resultReq.getInterest())
                .hobby(resultReq.getHobby())
                .picture(domain + "result/images/images.jpg") // 임시 사용
                .build();

        Result resultRst = resultRepository.save(result);
        convertResultToResultRes(resultRst, resultRes);

        return resultRes;
    }

    public ResultRes selectResult(String id) {
        ResultRes resultRes = new ResultRes();
        Integer resultId  = null;
        try {
            resultId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            log.error("id가 올바르지 않습니다. {}", id);
            throw new CommonException(CommonErrorCode.INVALID_ID);
        }

        Result resultRst = resultRepository.findById(resultId).orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND));
        convertResultToResultRes(resultRst, resultRes);

        return resultRes;
    }

    public ResultStatRankRes selectResultStatRank(String sex) {
        ResultStatRankRes resultStatRankRes = new ResultStatRankRes();

        List<ResultStatRankData> mbtiStatRankData = new ArrayList<>();
        List<ResultStatRank> mbtiStatRank = resultRepository.getAverageRank("mbti", sex);
        convertResultStatRankToResultRankData(mbtiStatRank, mbtiStatRankData);
        resultStatRankRes.setMbtiStatRankData(mbtiStatRankData);

        List<ResultStatRankData> looklikeRankData = new ArrayList<>();
        List<ResultStatRank> looklikeStatRank = resultRepository.getAverageRank("looklike", sex);
        convertResultStatRankToResultRankData(looklikeStatRank, looklikeRankData);
        resultStatRankRes.setLooklikeStatRankData(looklikeRankData);

        List<ResultStatRankData> heightStatRankData = new ArrayList<>();
        List<ResultStatRank> heightStatRank = resultRepository.getAverageRank("height", sex);
        convertResultStatRankToResultRankData(heightStatRank, heightStatRankData);
        resultStatRankRes.setHeightStatRankData(heightStatRankData);

        List<ResultStatRankData> eyeshapeStatRankData = new ArrayList<>();
        List<ResultStatRank> eyeshapeStatRank = resultRepository.getAverageRank("eyeshape", sex);
        convertResultStatRankToResultRankData(eyeshapeStatRank, eyeshapeStatRankData);
        resultStatRankRes.setEyeshapeStatRankData(eyeshapeStatRankData);

        List<ResultStatRankData> faceshapeStatRankData = new ArrayList<>();
        List<ResultStatRank> faceshapeStatRank = resultRepository.getAverageRank("faceshape", sex);
        convertResultStatRankToResultRankData(faceshapeStatRank, faceshapeStatRankData);
        resultStatRankRes.setFaceshapeStatRankData(faceshapeStatRankData);

        List<ResultStatRankData> fashionStatRankData = new ArrayList<>();
        List<ResultStatRank> fashionStatRank = resultRepository.getAverageRank("fashion", sex);
        convertResultStatRankToResultRankData(fashionStatRank, fashionStatRankData);
        resultStatRankRes.setFashionStatRankData(fashionStatRankData);

        List<ResultStatRankData> interestStatRankData = new ArrayList<>();
        List<ResultStatRank> interestStatRank = resultRepository.getAverageRank("interest", sex);
        convertResultStatRankToResultRankData(interestStatRank, interestStatRankData);
        resultStatRankRes.setInterestStatRankData(interestStatRankData);

        List<ResultStatRankData> hobbyStatRankData = new ArrayList<>();
        List<ResultStatRank> hobbyStatRank = resultRepository.getAverageRank("hobby", sex);
        convertResultStatRankToResultRankData(hobbyStatRank, hobbyStatRankData);
        resultStatRankRes.setHobbyStatRankData(hobbyStatRankData);

        return resultStatRankRes;
    }

    private void convertResultStatRankToResultRankData(List<ResultStatRank> resultStatRank, List<ResultStatRankData> resultStatRankData) {
        resultStatRank.forEach(resultStatRank1 -> {
            ResultStatRankData statRankData = new ResultStatRankData();
            statRankData.setCategory(resultStatRank1.getCategory());
            statRankData.setStat(resultStatRank1.getStat());
            statRankData.setRank(resultStatRank1.getStatRank());
            resultStatRankData.add(statRankData);
        });
    }


    private void convertResultToResultRes(Result result, ResultRes resultRes) {
        resultRes.setId(result.getId());
        resultRes.setAge(result.getAge());
        resultRes.setSex(result.getSex());
        resultRes.setMbti(result.getMbti());
        resultRes.setLookLike(result.getLooklike());
        resultRes.setHeight(result.getHeight());
        resultRes.setEyeShape(result.getEyeshape());
        resultRes.setFaceShape(result.getFaceshape());
        resultRes.setFashion(result.getFashion());
        resultRes.setInterest(result.getInterest());
        resultRes.setHobby(result.getHobby());
        resultRes.setPicture(result.getPicture());
    }

    public ResponseEntity<Resource> loadImage(String filename) {
        Resource resource = new FileSystemResource(uploadPath + filename);
        if (!resource.exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(uploadPath + filename);
            header.add("Content-type", Files.probeContentType(filePath));
        } catch(IOException e) {
            log.error("이미지를 불러오는대 실패하였습니다. {}", filename);
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}
