package com.myidol.result.file.entity;

import com.myidol.result.mgnt.entity.Result;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "result_file")
public class ResultFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('result_file_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "filename", nullable = false, length = Integer.MAX_VALUE)
    private String filename;

    @NotNull
    @Column(name = "filepath", nullable = false, length = Integer.MAX_VALUE)
    private String filepath;

    @NotNull
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "modifydate")
    private Instant modifydate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

}