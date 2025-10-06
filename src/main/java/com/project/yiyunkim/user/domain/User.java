package com.project.yiyunkim.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="user")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=100,nullable = false,unique = true)
    private String email;

    @Column(length = 20,nullable=false,unique=true)
    private String nickName;

    @Column(length=2048)
    private String imageUrl;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

}
