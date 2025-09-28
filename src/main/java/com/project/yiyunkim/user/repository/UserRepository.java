package com.project.yiyunkim.user.repository;

import com.project.yiyunkim.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByNickName(String nickName);
}
