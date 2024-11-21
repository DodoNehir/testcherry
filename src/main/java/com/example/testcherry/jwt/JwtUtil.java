package com.example.testcherry.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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

//  public String generateAccessToken(UserDetails userDetails) {
//    return generateToken(userDetails.getUsername());
//  }

//  public String getUsername(String accessToken) {
//    return getSubject(accessToken);
//  }

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

}
