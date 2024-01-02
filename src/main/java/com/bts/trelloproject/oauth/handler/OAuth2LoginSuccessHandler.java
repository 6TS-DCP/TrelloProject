package com.bts.trelloproject.oauth.handler;

import com.bts.trelloproject.global.common.CustomOAuthResponseEntity;
import com.bts.trelloproject.global.security.jwt.JwtUtil;
import com.bts.trelloproject.oauth.dto.OAuth2ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final int REFRESH_TOKEN_TIME = 60 * 24 * 14;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2ResponseDTO res = addTokensInHeader(authentication, response);
        settingResponse(response, CustomOAuthResponseEntity.success(res));
    }

    private OAuth2ResponseDTO addTokensInHeader(
            Authentication authentication, HttpServletResponse response) {

        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
        String username = (String) userDetails.getAttributes().get("username");

//        List<? extends GrantedAuthority> list = new ArrayList<>(authentication.getAuthorities());
//        String role = list.get(0).getAuthority();

        String accessToken = jwtUtil.createToken(username, true);
        String refreshToken = jwtUtil.createRefreshToken();

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        response.addCookie(setCookie(accessToken, JwtUtil.ACCESS_TOKEN_HEADER));
        response.addCookie(setCookie(refreshToken, JwtUtil.REFRESH_TOKEN_HEADER));


        return new OAuth2ResponseDTO();
    }

    private void settingResponse(HttpServletResponse response, CustomOAuthResponseEntity<?> res)
            throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String result = objectMapper.writeValueAsString(res);
        response.getWriter().write(result);
    }

    private Cookie setCookie(String token, String header) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        Cookie cookie = new Cookie(header, token);
        cookie.setPath("/");

        return cookie;
    }
}
