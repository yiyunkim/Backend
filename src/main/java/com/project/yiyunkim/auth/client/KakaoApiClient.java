package com.project.yiyunkim.auth.client;

import com.project.yiyunkim.auth.dto.response.KakaoUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



//카카오 로그인 과정에서 백엔드가 인가코드로 액세스 토큰을 받고
//액세스 토큰으로 사용자 정보를 받아오는 로직
@Component
@Slf4j
public class KakaoApiClient {

    @Value("${kakao.oauth.client-id}")
    private String kakaoClientId;

    @Value("${kakao.oauth.redirect-uri}")
    private String kakaoRedirectUri;

    private final RestTemplate restTemplate;

    public KakaoApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //액세스 토큰으로 카카오 사용자 정보 조회
    public KakaoUserInfo getUserInfo(String accessToken){
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request=new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfo> response=restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                KakaoUserInfo.class
        );

        return response.getBody();
    }

    // 프론트에서 받은 인가코드 -> 카카오에 토큰 발급 요청 -> 엑세스 토큰 발급
    public String requestAccessToken(String authorizationCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", authorizationCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token",
                request,
                String.class
        );

        log.debug("카카오 응답 상태: " + response.getStatusCode());
        log.debug("카카오 응답 바디: " + response.getBody());

        JSONObject json = new JSONObject(response.getBody());

        return json.getString("access_token");
    }
}
