package com.myidol.result.mgnt.service;

import com.myidol.common.exception.CommonErrorCode;
import com.myidol.common.exception.CommonException;
import com.myidol.common.exception.ErrorCode;
import com.myidol.result.category.entity.ResultCategory;
import com.myidol.result.category.service.ResultCategoryService;
import com.myidol.result.file.entity.ResultFile;
import com.myidol.result.file.service.ResultFileService;
import com.myidol.result.mgnt.dto.ResultReq;
import com.myidol.result.mgnt.dto.ResultRes;
import com.myidol.result.mgnt.dto.ResultStatRankData;
import com.myidol.result.mgnt.dto.ResultStatRankRes;
import com.myidol.result.mgnt.entity.Result;
import com.myidol.result.mgnt.entity.ResultStatRank;
import com.myidol.result.mgnt.repository.ResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final ResultCategoryService resultCategoryService;
    private final ResultFileService resultFileService;

    @Value("${file.path}")
    private String uploadPath;

    @Value("${domain}")
    private String domain;

    private final WebClient webClient;

    @Transactional
    public ResultRes saveResult(ResultReq resultReq) {
        ResultRes resultRes = new ResultRes();

        List<String> hobbyList = resultReq.getHobbyList();

        String hobby1 = null;
        String hobby2 = null;

        // 임시 취미는 무조건 2개만 적용
        if (hobbyList != null && !hobbyList.isEmpty()) {
            if (hobbyList.size() == 1) {
                hobby1 = hobbyList.get(0);
            } else {
                hobby1 = hobbyList.get(0);
                hobby2 = hobbyList.get(1);
            }
        }
        Result result = Result.builder()
                .age(resultReq.getAge())
                .sex(resultReq.getSex())
                .mbti(resultReq.getMbti())
                .looklike(resultReq.getLookLike())
                .height(resultReq.getHeight())
                .eyeshape(resultReq.getEyeShape())
                .faceshape(resultReq.getFaceShape())
                .fashion(resultReq.getFashion())
                .hobby1(hobby1)
                .hobby2(hobby2)
                //.picture(domain + "result/images/images.jpg") // 임시 사용
                .build();

        Result resultRst = resultRepository.save(result);

        // 취미 등록
        if (StringUtils.isNotBlank(hobby1)) {
            ResultCategory resultCategory = ResultCategory.builder()
                    .category("hobby")
                    .content(hobby1)
                    .result(resultRst)
                    .build();
            resultCategoryService.saveResultCategory(resultCategory);
        }

        if (StringUtils.isNotBlank(hobby2)) {
            ResultCategory resultCategory = ResultCategory.builder()
                    .category("hobby")
                    .content(hobby2)
                    .result(resultRst)
                    .build();
            resultCategoryService.saveResultCategory(resultCategory);
        }

        // 이미지 등록
        callApi(resultRst);
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
        String hobby = null;
        if (StringUtils.isNotBlank(result.getHobby1())) {
            hobby = result.getHobby1();
        }
        if (StringUtils.isNotBlank(result.getHobby2())) {
            if (StringUtils.isNotBlank(result.getHobby1())) {
                hobby += " , ";
            }
            hobby += result.getHobby2();
        }
        resultRes.setHobby(hobby);
        resultRes.setPicture("https://devapi.tikitaka.chat/result/image/"+result.getId());
    }

    public ResponseEntity<Resource> loadImage(String id) {
        if (StringUtils.isBlank(id)) {
            log.error("이미지 조회 정보가 잘못되었습니다. {}", id);
            throw new CommonException(CommonErrorCode.INVALID_ID);
        }

        Integer resultId = Integer.parseInt(id);
        ResultFile resultFile = resultFileService.selectResultFileByResultId(resultId);
        String path = resultFile.getFilepath();
        String filename = resultFile.getFilename();
        Resource resource = new FileSystemResource(path + filename);
        if (!resource.exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(path + filename);
            header.add("Content-type", Files.probeContentType(filePath));
        } catch(IOException e) {
            log.error("이미지를 불러오는대 실패하였습니다. {}", filename);
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    private void callApi(Result result) {
        /*WebClientReq webClientReq = new WebClientReq();
        webClient.post().uri("/api").bodyValue(webClientReq).retrieve().bodyToMono(WebClientRes.class).subscribe(webClientRes1 -> {
            String picture = webClientRes1.getPicture();
            File file = imageUploadToSever("https://devapi.tikitaka.chat/result/images/images.jpg");
            ResultFile resultFile = ResultFile.builder()
                    .filename(file.getName())
                    .filepath(file.getPath())
                    .status("Y")
                    .result(result)
                    .build();
        });*/
        File file = imageUploadToSever("https://devapi.tikitaka.chat/result/image/3", String.valueOf(result.getId()));
        ResultFile resultFile = ResultFile.builder()
                .filename(file.getName())
                .filepath(file.getParent() + File.separator)
                .status("Y")
                .result(result)
                .build();
        resultFileService.saveResultFile(resultFile);
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder --------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(!uploadPathFolder.exists()) {
            boolean mkdirs = uploadPathFolder.mkdirs();
            log.info("-------------------makeFolder------------------");
            log.info("uploadPathFolder.exists() : {}", uploadPathFolder.exists());
            log.info("mkdirs : {}", mkdirs);
        }
        return folderPath;
    }

    public File imageUploadToSever(String imagePath, String id) {
        File file = null;
        String uuid = UUID.randomUUID().toString();
        long currentTimeMills = Timestamp.valueOf(LocalDateTime.now()).getTime();
        String folderPath = makeFolder();
        String filePath = uploadPath + File.separator + folderPath + File.separator;
        String fileName = uuid + "_" + id + "_" + currentTimeMills + ".jpeg";
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(imagePath));
            file = new File(filePath + fileName);
            ImageIO.write(image, fileName.substring(fileName.lastIndexOf(".") + 1), file);
        } catch (Exception e) {
            log.error("imageUploadToSever error {}", e.getMessage());
           // throw new RuntimeException(e.getMessage());
        }
        return file;
    }
}
