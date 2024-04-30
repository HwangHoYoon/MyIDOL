package com.myidol.result.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Table(name = "result_detail")
public class ResultDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('result_detail_id_seq'")
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
    @JoinColumn(name = "result_master_id", nullable = false)
    private ResultMaster resultMaster;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

}