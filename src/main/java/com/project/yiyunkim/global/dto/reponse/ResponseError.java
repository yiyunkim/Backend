package com.project.yiyunkim.global.dto.reponse;

import lombok.Data;

@Data
public class ResponseError {

    private String path;
    private String messageDetail;
    private String errorDetail;
}
