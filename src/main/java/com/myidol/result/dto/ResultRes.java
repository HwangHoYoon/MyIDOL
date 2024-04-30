package com.myidol.result.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResultRes {

    @Schema(description = "고유번호", example = "1", name = "id")
    private Integer id;

    @Schema(description = "나이", example = "10대", name = "age")
    private String age;

    @Schema(description = "성별", example = "남성", name = "sex")
    private String sex;

    @Schema(description = "mbti", example = "ISTJ", name = "mbti")
    private String mbti;

    @Schema(description = "닯은꼴", example = "홍길동", name = "lookLike")
    private String lookLike;

    @Schema(description = "키", example = "156cm ~ 160cm", name = "height")
    private String height;

    @Schema(description = "눈", example = "무쌍 실눈", name = "eyeShape")
    private String eyeShape;

    @Schema(description = "얼굴상", example = "강아지상", name = "faceShape")
    private String faceShape;

    @Schema(description = "패션", example = "미니멀", name = "fashion")
    private String fashion;

    @Schema(description = "관심사", example = "영화", name = "interest")
    private String interest;

    @Schema(description = "취미", example = "축구", name = "hobby")
    private String hobby;

    @Schema(description = "사진", example = "https://", name = "picture")
    private String picture;
}
