package com.bts.trelloproject.oauth.dto;

import com.bts.trelloproject.user.constant.Provider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2UserProfile {

    private String email;
    private String name;
    private String imageUrl;
    private Provider provider;

    @Builder
    private OAuth2UserProfile(String email, String name, String imageUrl, Provider provider) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }
}

