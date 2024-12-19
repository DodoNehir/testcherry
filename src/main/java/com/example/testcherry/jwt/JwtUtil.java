package com.example.testcherry.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
  //  private static final SecretKey key = Jwts.SIG.HS256.key().build();
  private final SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret-key}") String secret) {
    this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
//    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }

  public void setTokensInResponse(HttpServletResponse response,
      String accessToken, String refreshToken, String userAgent) throws IOException {
    // accessToken 을 Json 으로 전송하는 것은 동일
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Map<String, String> tokens = new HashMap<>();
    tokens.put("accessToken", accessToken);

    if (isMobileRequest(userAgent)) {
      // 모바일 요청은 refresh 도 json 에 포함하여 응답
      tokens.put("refreshToken", refreshToken);
    } else {
      // 웹 요청은 refresh 를 쿠키에 담아서 응답
      response.addCookie(createCookie("refreshToken", refreshToken));
    }

    ObjectMapper objectMapper = new ObjectMapper();
    response.getWriter().write(objectMapper.writeValueAsString(tokens));
  }

  public String generateToken(String category, String username, String role, Long expiration) {
    // refresh를 들고 오면 access 사용 불가하도록
    return Jwts.builder()
        .claim("category", category)
        .claim("username", username)
        .claim("role", role)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(secretKey)
        .compact();
  }

  public String getCategory(String token) {
    try {
      return Jwts.parser().verifyWith(secretKey).build()
          .parseSignedClaims(token)
          .getPayload().get("category", String.class);
    } catch (JwtException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  public String getUsername(String token) {
    try {
      return Jwts.parser().verifyWith(secretKey).build()
          .parseSignedClaims(token)
          .getPayload().get("username", String.class);
    } catch (JwtException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  public String getRole(String token) {
    try {
      return Jwts.parser().verifyWith(secretKey).build()
          .parseSignedClaims(token)
          .getPayload().get("role", String.class);
    } catch (JwtException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  public boolean isExpired(String token) {
    try {
      return Jwts.parser().verifyWith(secretKey).build()
          .parseSignedClaims(token)
          .getPayload().getExpiration().before(new Date());
    } catch (JwtException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  private boolean isMobileRequest(String userAgent) {
    return userAgent != null &&
        (userAgent.toLowerCase().contains("android")
            || userAgent.toLowerCase().contains("iphone"));
  }

  private Cookie createCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(24 * 60 * 60);
    cookie.setSecure(true); // HTTPS
    cookie.setPath("/"); // 쿠키가 적용될 범위
    cookie.setHttpOnly(true);
    return cookie;
  }
}
