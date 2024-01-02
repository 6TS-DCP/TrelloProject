package com.bts.trelloproject.oauth.service;

import com.bts.trelloproject.oauth.OAuth2Attributes;
import com.bts.trelloproject.oauth.dto.OAuth2RequestDTO;
import com.bts.trelloproject.user.constant.UserRoleEnum;
import com.bts.trelloproject.user.entity.User;
import com.bts.trelloproject.user.repository.UserRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); // Oauth 서비스에서 가져온 유저 정보를 담고 있음

        String providerType =
                userRequest
                        .getClientRegistration() // ex) naver, google, github
                        .getRegistrationId()
                        .toUpperCase();

        String userNameAttributeName =
                userRequest
                        .getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName();
        // google = sub 가 고유값, naver = id 가 고유값

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2RequestDTO oAuth2RequestDTO = OAuth2Attributes.extract(providerType, attributes);

        User saveUser = userRepository.findByOauthId(oAuth2RequestDTO.getOauthId());

        if (saveUser == null) {
            saveUser = save(oAuth2RequestDTO);
        }

        Map<String, Object> customAttribute =
                customAttribute(attributes, userNameAttributeName, oAuth2RequestDTO, saveUser.getUsername());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(UserRoleEnum.USER.getValue())),
                customAttribute,
                userNameAttributeName);
    }

    private String makeRandomName() {
        String randomName;
        do {
            randomName = generateRandomName();
        } while (userRepository.existsByUsername(randomName));
        return randomName;
    }

    private String generateRandomName() {
        return "DEFAULT_NAME" + UUID.randomUUID().toString().substring(0, 6);
    }

    private User save(OAuth2RequestDTO oAuth2RequestDTO) {
        //String password = passwordEncoder.encode(oAuth2RequestDTO.getOauthId());
        User user =
                User.builder()
                        .username(makeRandomName())
                        .email(oAuth2RequestDTO.getEmail())
                        .oauthId(oAuth2RequestDTO.getOauthId())
                        .profileImageUrl(oAuth2RequestDTO.getImageUrl())
                        .provider(oAuth2RequestDTO.getProvider())
                        .userRoleEnum(UserRoleEnum.USER)
                        //.password(password)
                        .build();

        return userRepository.save(user);
    }

    private Map<String, Object> customAttribute(
            Map<String, Object> attributes,
            String userNameAttributeName,
            OAuth2RequestDTO oAuth2RequestDTO,
            String username) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", oAuth2RequestDTO.getProvider());
        customAttribute.put("username", username);
        customAttribute.put("email", oAuth2RequestDTO.getEmail());
        customAttribute.put("oAuthId", oAuth2RequestDTO.getOauthId());
        return customAttribute;
    }
}
