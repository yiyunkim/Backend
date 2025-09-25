package com.project.yiyunkim.security.exception;

import com.project.yiyunkim.global.exception.CustomException;
import com.project.yiyunkim.global.exception.ErrorCode;

public class TokenException extends CustomException {

    private final SecurityErrorCode errorCode;


    public TokenException(SecurityErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public SecurityErrorCode getErrorCode() {
        return errorCode;
    }
}
