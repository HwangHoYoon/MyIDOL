package com.myidol.result.category.entity;

import com.myidol.result.mgnt.entity.Result;
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
@Table(name = "result_category")
public class ResultCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('result_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @NotNull
    @Column(name = "category", nullable = false, length = 30)
    private String category;

    @Size(max = 200)
    @NotNull
    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

}