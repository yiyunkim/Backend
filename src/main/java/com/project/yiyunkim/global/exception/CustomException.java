package com.project.yiyunkim.global.exception;

import com.project.yiyunkim.global.dto.reponse.CommonResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public static <T> ResponseEntity<CommonResponse<T>> createErrorResponse(ErrorCode errorCode, T data) {
        CommonResponse<T> response = CommonResponse.<T>builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .data(data)
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
}
