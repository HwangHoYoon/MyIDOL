package com.myidol.result.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.Instant;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('result_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "age", nullable = false, length = 10)
    private String age;

    @Size(max = 10)
    @NotNull
    @Column(name = "sex", nullable = false, length = 10)
    private String sex;

    @Size(max = 100)
    @NotNull
    @Column(name = "mbti", nullable = false, length = 100)
    private String mbti;

    @Size(max = 100)
    @NotNull
    @Column(name = "looklike", nullable = false, length = 100)
    private String looklike;

    @Size(max = 50)
    @NotNull
    @Column(name = "height", nullable = false, length = 50)
    private String height;

    @Size(max = 100)
    @NotNull
    @Column(name = "eyeshape", nullable = false, length = 100)
    private String eyeshape;

    @Size(max = 100)
    @NotNull
    @Column(name = "faceshape", nullable = false, length = 100)
    private String faceshape;

    @Size(max = 100)
    @NotNull
    @Column(name = "fashion", nullable = false, length = 100)
    private String fashion;

    @Size(max = 100)
    @NotNull
    @Column(name = "interest", nullable = false, length = 100)
    private String interest;

    @Size(max = 100)
    @NotNull
    @Column(name = "hobby", nullable = false, length = 100)
    private String hobby;

    @Size(max = 2083)
    @Column(name = "picture", length = 2083)
    private String picture;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reg_date")
    private Instant regDate;

}