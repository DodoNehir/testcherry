package com.example.testcherry.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
  //  private static final SecretKey key = Jwts.SIG.HS256.key().build();
  private final SecretKey key;

  public JwtService(@Value("${jwt.secret-key}") SecretKey key) {
    this.key = Keys.hmacShaKeyFor(key.getEncoded());
  }

  public String generateAccessToken(UserDetails userDetails) {
    return generateToken(userDetails.getUsername());
  }

  public String getUsername(String accessToken) {
    return getSubject(accessToken);
  }

  private String generateToken(String subject) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 24); // 24시간

    return Jwts.builder().subject(subject).signWith(key)
        .issuedAt(now)
        .expiration(expiration)
        .compact();
  }

  private String getSubject(String token) {
    try {
      return Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token)
          .getPayload()
          .getSubject();
    } catch (JwtException e) {
      logger.error(e.getMessage());
      throw e;
    }

  }

}
