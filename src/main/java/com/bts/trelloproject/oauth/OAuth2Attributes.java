package com.bts.trelloproject.oauth;

import com.bts.trelloproject.global.common.StatusEnum;
import com.bts.trelloproject.global.exception.CustomException;
import com.bts.trelloproject.oauth.dto.OAuth2RequestDTO;
import com.bts.trelloproject.user.constant.Provider;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Attributes {
    GITHUB("GITHUB") {
        @Override
        public OAuth2RequestDTO of(Map<String, Object> attributes) {
            return OAuth2RequestDTO.builder()
                    .oauthId(attributes.get("id").toString())
                    .email((String) attributes.get("email"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .provider(Provider.GITHUB)
                    .build();
        }
    },
    NAVER("NAVER") {
        @Override
        @SuppressWarnings("unchecked")
        public OAuth2RequestDTO of(Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return OAuth2RequestDTO.builder()
                    .oauthId(attributes.get("id").toString())
                    .email((String) response.get("email"))
                    .imageUrl((String) response.get("profile_image"))
                    .provider(Provider.NAVER)
                    .build();
        }
    },
    GOOGLE("GOOGLE") {
        @Override
        public OAuth2RequestDTO of(Map<String, Object> attributes) {
            return OAuth2RequestDTO.builder()
                    .oauthId((String) attributes.get("sub"))
                    .email((String) attributes.get("email"))
                    .imageUrl((String) attributes.get("picture"))
                    .provider(Provider.GOOGLE)
                    .build();
        }
    };

    private final String providerName;

    public static OAuth2RequestDTO extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values()) // 해당 enum 의 요소들을 순서대로 순회
                .filter(provider -> providerName.equals(provider.providerName))
                .findAny()
                .orElseThrow(() -> new CustomException(StatusEnum.INVALID_OAUTH_PROVIDER))
                .of(attributes);
    }

    public abstract OAuth2RequestDTO of(Map<String, Object> attributes); // 추상 메서드로 구현
}
