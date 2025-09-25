package com.project.yiyunkim.redis.service;

import com.project.yiyunkim.redis.entity.RefreshToken;
import com.project.yiyunkim.redis.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(Long userId, String refreshToken, Long ttl){
        RefreshToken token=RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .ttl(ttl)
                .build();
        refreshTokenRepository.save(token);
    }

    public Optional<RefreshToken> findRefreshToken(Long userId){
        return refreshTokenRepository.findById(userId);
    }

    public boolean deleteRefreshToken(Long userId){
        if(refreshTokenRepository.existsById(userId)){
            refreshTokenRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
