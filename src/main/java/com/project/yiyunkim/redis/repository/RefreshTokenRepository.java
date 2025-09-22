package com.project.yiyunkim.redis.repository;

import com.project.yiyunkim.redis.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
