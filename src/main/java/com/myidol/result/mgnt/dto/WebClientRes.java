package com.myidol.result.mgnt.dto;

import lombok.Data;

@Data
public class WebClientRes {
    private String code;
    private ResultRes data;
    private String message;
}
