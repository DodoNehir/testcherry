package com.example.testcherry.service;

import com.example.testcherry.exception.InvalidJwtException;
import com.example.testcherry.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReissueService {

  private final JwtUtil jwtUtil;

  public String reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

    String refresh = null;
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("refresh")) {
        refresh = cookie.getValue();
      }
    }

    if (refresh == null) {
      throw new InvalidJwtException("refresh");
    }

    try {
      jwtUtil.isExpired(refresh);
    } catch (ExpiredJwtException e) {
      throw e;
    }

    // refresh 인지 확인
    String category = jwtUtil.getCategory(refresh);

    if (!category.equals("refresh")) {
      throw new InvalidJwtException("refresh");
    }

    // 새로운 토큰 생성
    String username = jwtUtil.getUsername(refresh);
    String role = jwtUtil.getRole(refresh);

    return jwtUtil.generateToken("access", username, role, 60 * 60 * 1000L);
  }

}
