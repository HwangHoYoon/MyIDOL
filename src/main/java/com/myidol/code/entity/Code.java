package com.myidol.code.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "code")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('code_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @NotNull
    @Column(name = "main_category", nullable = false, length = 30)
    private String mainCategory;

    @Size(max = 30)
    @NotNull
    @Column(name = "sub_category", nullable = false, length = 30)
    private String subCategory;

    @Size(max = 100)
    @NotNull
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Size(max = 2083)
    @Column(name = "picture", length = 2083)
    private String picture;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

}