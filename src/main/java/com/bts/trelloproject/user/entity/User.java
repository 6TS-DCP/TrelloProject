package com.bts.trelloproject.user.entity;

import com.bts.trelloproject.board.entity.Boards;
import com.bts.trelloproject.global.common.BaseLastModifiedTimeEntity;
import com.bts.trelloproject.user.constant.Provider;
import com.bts.trelloproject.user.constant.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseLastModifiedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true, length = 10)
    private String introduce;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String profileImageUrl;

    @Column(nullable = true)
    private String oauthId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(
            Long userId,
            String username,
            String password,
            String email,
            String introduce,
            String profileImageUrl,
            String oauthId,
            UserRoleEnum userRoleEnum,
            Provider provider) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
        this.oauthId = oauthId;
        this.role = userRoleEnum;
        this.provider = provider;
    }

    public void updateProfile(String Introduce) {
        this.introduce = Introduce;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}