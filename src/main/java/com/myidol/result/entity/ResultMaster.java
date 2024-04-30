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
@Table(name = "result_master")
public class ResultMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('result_master_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @Column(name = "age", length = 10)
    private String age;

    @Size(max = 10)
    @Column(name = "sex", length = 10)
    private String sex;

    @Size(max = 2083)
    @Column(name = "picture", length = 2083)
    private String picture;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

}