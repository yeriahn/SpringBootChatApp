package com.noti.chatapp.config.security;

import com.noti.chatapp.domain.setting.Member;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey; //application.yml에 secret키를 작성하면 해당 value를 가져올 수 있다.

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60 * 60; //Token 토큰 유효 시간 1시간
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2; //Refresh 토큰

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    private final UserDetailsService userDetailsService;

    /**
     * 객체 초기화, secretKey를 Base64로 인코딩한다.
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Access Token을 형성
     * @param member
     * @return
     */
    public String generateJwtToken(Member member) {
        return doGenerateJwtToken(member.getMemberId(), TOKEN_VALIDATION_SECOND);
    }

    /**
     * Refresh Token을 형성
     * @param member
     * @return
     */
    public String generateRefreshJwtToken(Member member) {
        return doGenerateJwtToken(member.getMemberId(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    /**
     * 토큰 생성, payload에 담길 값은 name 추후 삭제 예정.
     */
    public String generateToken(String name) {
        return Jwts.builder()
                .setId(name)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발행일자
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDATION_SECOND)) // 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    public String doGenerateJwtToken(String member, long expireTime) {
        Date now = new Date();
        return Jwts.builder()
                .setId(member)
                .setHeader(createHeader())
                .setExpiration(new Date(now.getTime() + expireTime)) // 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    /**
     * JWT 토큰에서 인증 정보 조회
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserNameFromJwt(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Jwt Token을 복호화 하여 이름을 얻는다.
     */
    public String getUserNameFromJwt(String jwt) {
        log.info("getUserNameFromJwt : {}",getClaims(jwt).getBody().getId());
        return getClaims(jwt).getBody().getId();
    }

    /**
     * Request의 Header에서 token 값을 가져온다. "X-AUTH-TOKEN" : "TOKEN값'
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    /**
     * Jwt Token의 유효성을 체크한다.
     */
    public boolean validateToken(String jwt) {
        return this.getClaims(jwt) != null;
    }

    private Jws<Claims> getClaims(String jwt) {
        try {
            log.info("getClaims : "+Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt));
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromJwt(token);

        log.info("validateToken username : {}", username);
        log.info("validateToken userDetails.getUsername : {}", userDetails.getUsername());
        log.info("validateToken(token) : {}", validateToken(token));
        return (username.equals(userDetails.getUsername()) && validateToken(token));
    }

}
