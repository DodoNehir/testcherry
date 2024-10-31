package com.example.testcherry.service;

import com.example.testcherry.dto.MemberDto;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final SecretKey key = Jwts.SIG.HS256.key().build();

  public String generateAccessToken(MemberDto memberDto) {
    return generateToken(memberDto.name());
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
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();

  }

}
