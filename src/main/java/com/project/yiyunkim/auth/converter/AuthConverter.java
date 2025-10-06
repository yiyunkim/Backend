package com.project.yiyunkim.auth.converter;

import com.project.yiyunkim.auth.dto.response.JwtLoginResponse;
import com.project.yiyunkim.user.domain.User;

public class AuthConverter {

    public static JwtLoginResponse toJwtLoginResponse(User user, String accessToken, String refreshToken) {
        return JwtLoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .jwtAccessToken("Bearer " + accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }


}
