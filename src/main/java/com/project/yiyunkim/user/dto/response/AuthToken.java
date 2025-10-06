package com.project.yiyunkim.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthToken {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;
}
