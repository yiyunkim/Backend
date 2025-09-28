package com.project.yiyunkim.auth.controller;

import com.project.yiyunkim.auth.client.KakaoApiClient;
import com.project.yiyunkim.auth.dto.response.JwtLoginResponse;
import com.project.yiyunkim.auth.dto.response.TokenResponse;
import com.project.yiyunkim.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {

    private final KakaoApiClient kakaoApiClient;
    private final AuthService authService;

    @PostMapping("/login/kakao")
    public ResponseEntity<JwtLoginResponse> kakaoLoginToken(@RequestParam String code){
        JwtLoginResponse loginResponse = authService.loginOrRegister(code);


        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, loginResponse.getJwtAccessToken())
                .body(loginResponse);
    }

    //백엔드 확인용
    @PostMapping("/token/reissue")
    @Operation(summary = "백엔드 개발용 API")
    public ResponseEntity<TokenResponse> reissueAccessToken(@RequestParam String refreshToken) {

        // 새 AccessToken 재발급 시도
        String newAccessToken = authService.reissueAccessToken(refreshToken);

        TokenResponse response = new TokenResponse(newAccessToken);

        return ResponseEntity.ok(response);
    }

}
