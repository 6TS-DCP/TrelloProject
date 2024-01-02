package com.bts.trelloproject.global.security.jwt;

import static com.bts.trelloproject.global.common.StatusEnum.EXPIRED_JWT_TOKEN_EXCEPTION;
import static com.bts.trelloproject.global.common.StatusEnum.INVALID_JWT_SIGNATURE_EXCEPTION;

import com.bts.trelloproject.global.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ACCESS_TOKEN_HEADER = "AccessToken";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";


    public static final String AUTHORIZATION_KEY = "auth";

    public static final String BEARER_PREFIX = "Bearer ";
    //테스트용 4시간
    private final long TOKEN_TIME = 240 * 60 * 1000L;
    private final long REFRESH_TOKEN_TIME = 240 * 60 * 1000L;


    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String username, boolean forLogin) {
        Date date = new Date();
        long token_time = forLogin ? TOKEN_TIME : 0;

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + token_time))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();

        return BEARER_PREFIX
                + Jwts.builder()
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME)) // 만료 시간
                .setIssuedAt(now) // 발급일
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘 (HS256)
                .compact();
    }

    // HttpServletRequest 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {

        String tokenWithBearer = request.getHeader("AUTHORIZATION");

        if(StringUtils.hasText(tokenWithBearer) && tokenWithBearer.startsWith(BEARER_PREFIX)) {

            return tokenWithBearer.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String tokenValue) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenValue);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new CustomException(INVALID_JWT_SIGNATURE_EXCEPTION);
        } catch (ExpiredJwtException e) {
            throw new CustomException(EXPIRED_JWT_TOKEN_EXCEPTION);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfo(String tokenValue) {

        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(tokenValue).getBody();
    }
}
