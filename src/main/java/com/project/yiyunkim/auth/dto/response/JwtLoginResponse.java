package com.project.yiyunkim.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtLoginResponse {

    private Long userId;
    private String email;
    private String nickName;
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
