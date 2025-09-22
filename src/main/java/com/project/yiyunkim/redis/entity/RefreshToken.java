package com.project.yiyunkim.redis.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("refreshToken")
@Getter
public class RefreshToken {

    @Id
    private Long userId;

    private String refreshToken;

    @TimeToLive
    private Long ttl;

    @Builder
    public RefreshToken(Long userId, String refreshToken, Long ttl){
        this.userId=userId;
        this.refreshToken=refreshToken;
        this.ttl=ttl;
    }
}
