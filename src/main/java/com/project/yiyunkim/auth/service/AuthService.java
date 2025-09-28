package com.project.yiyunkim.auth.service;

import com.project.yiyunkim.auth.client.KakaoApiClient;
import com.project.yiyunkim.auth.converter.AuthConverter;
import com.project.yiyunkim.auth.dto.response.KakaoUserInfo;
import com.project.yiyunkim.auth.dto.response.JwtLoginResponse;
import com.project.yiyunkim.redis.entity.RefreshToken;
import com.project.yiyunkim.redis.service.RefreshTokenRedisService;
import com.project.yiyunkim.security.exception.SecurityErrorCode;
import com.project.yiyunkim.security.exception.TokenException;
import com.project.yiyunkim.security.jwt.JwtProvider;
import com.project.yiyunkim.user.domain.User;
import com.project.yiyunkim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j

public class AuthService {

    private final UserRepository userRepository;
    private final KakaoApiClient kakaoApiClient;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRedisService refreshTokenRedisService;


    @Value("${jwt.expiration.refresh}")
    private Long REFRESH_TOKEN_EXPIRE_TIME;

    public JwtLoginResponse loginOrRegister(String code){

        //1. 인가 코드로 카카오 access token 요청
        String kakaoAccessToken= kakaoApiClient.requestAccessToken(code);

        //2. 카카오 access token으로 사용자 정보 조회
        KakaoUserInfo kakaoUserInfo=kakaoApiClient.getUserInfo(kakaoAccessToken);
        String email=kakaoUserInfo.getKakaoAccount().getEmail();
        String profileImage=kakaoUserInfo.getKakaoAccount().getProfile().getProfileImageUrl();

        log.debug("카카오 사용자 정보: {}",kakaoUserInfo);

        //3. 회원 조회 또는 생성
        User user=userRepository.findByEmail(email).orElseGet(()->{
            String nicknameToUse=generateRandomNickname();
            return userRepository.save(User.builder()
                    .email(email)
                    .nickName(nicknameToUse)
                    .imageUrl(profileImage)
                    .enabled(false)
                    .build());
        });

        //4. Access & Refresh Token 생성
        String userIdStr=user.getId().toString();
        Authentication authentication=jwtProvider.getAuthenticationFromUserId(userIdStr);
        String accessToken= jwtProvider.generateAccessToken(authentication,userIdStr);
        String refreshToken= jwtProvider.generateRefreshToken(authentication,userIdStr);

        //5. Refresh Token을 Redis에 저장
        refreshTokenRedisService.saveRefreshToken(Long.valueOf(user.getId().toString()),refreshToken,REFRESH_TOKEN_EXPIRE_TIME);

        //6. JwtLoginResponse 반환
        return AuthConverter.toJwtLoginResponse(user,accessToken, refreshToken);

    }


    private String generateRandomNickname(){
        for(int i=0;i<5;i++){
            String nickname="user_"+ UUID.randomUUID().toString().substring(0,4);
            boolean exists=userRepository.existsByNickName(nickname);
            if(!exists){
                return nickname;
            }
        }
        return "user_"+UUID.randomUUID().toString().replace("-","");
    }

    //백엔드 확인용
    public String reissueAccessToken(String refreshToken) {

        // refreshToken 유효성 검사
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new TokenException(SecurityErrorCode.REFRESH_EXPIRED);
        }

        // refreshToken에서 userId 추출
        String userId = jwtProvider.getSubject(refreshToken);

        // redis에서 refreshToken 검증
        Optional<RefreshToken> optionalRefresh = refreshTokenRedisService.findRefreshToken(Long.parseLong(userId));

        if (optionalRefresh.isEmpty() || !optionalRefresh.get().getRefreshToken().equals(refreshToken)) {
            throw new TokenException(SecurityErrorCode.REFRESH_EXPIRED);
        }

        // 새 access token 생성
        Authentication authentication = jwtProvider.getAuthenticationFromUserId(userId);
        return jwtProvider.generateAccessToken(authentication, userId);
    }
}
