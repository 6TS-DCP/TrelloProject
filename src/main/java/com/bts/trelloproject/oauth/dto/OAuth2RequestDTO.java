package com.bts.trelloproject.oauth.dto;

import com.bts.trelloproject.user.constant.Provider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2RequestDTO {

    private String oauthId;
    private String email;
    private String imageUrl;
    private Provider provider;

    @Builder
    private OAuth2RequestDTO(String oauthId, String email, String imageUrl, Provider provider) {
        this.oauthId = oauthId;
        this.email = email;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }
}
