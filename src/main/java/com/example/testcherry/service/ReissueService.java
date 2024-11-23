package com.example.testcherry.service;

import com.example.testcherry.exception.InvalidJwtException;
import com.example.testcherry.jwt.JwtUtil;
import com.example.testcherry.model.entity.Refresh;
import com.example.testcherry.repository.RefreshReposiotry;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReissueService {

  private final JwtUtil jwtUtil;
  private final RefreshReposiotry refreshReposiotry;

  public HttpServletResponse reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

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

    // expired 확인
    try {
      jwtUtil.isExpired(refresh);
    } catch (ExpiredJwtException e) {
      throw e;
    }

    // refresh 확인
    String category = jwtUtil.getCategory(refresh);
    if (!category.equals("refresh")) {
      throw new InvalidJwtException("refresh");
    }

    // 저장되어 있는 refresh 확인
    boolean exists = refreshReposiotry.existsByRefreshToken(refresh);
    if (!exists) {
      throw new InvalidJwtException("refresh");
    }

    // 새로운 토큰 생성
    String username = jwtUtil.getUsername(refresh);
    String role = jwtUtil.getRole(refresh);

    String newAccessToken = jwtUtil.generateToken("access", username, role, 60 * 60 * 1000L);
    String newRefreshToken = jwtUtil.generateToken("refresh", username, role, 24 * 60 * 60 * 1000L);

    // 기존 refresh 토큰 삭제 & 새로운 refresh 토큰 추가
    refreshReposiotry.deleteByRefreshToken(refresh);
    saveRefreshEntity(username, newRefreshToken, 24 * 60 * 60 * 1000L);

    response.setHeader("access", newAccessToken);
    response.addCookie(createCookie("refresh", newRefreshToken));

    return response;
  }

  private Cookie createCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(24 * 60 * 60);
//    cookie.setSecure(true); // HTTPS
//    cookie.setPath("/"); // 쿠키가 적용될 범위
    cookie.setHttpOnly(true);
    return cookie;
  }

  private void saveRefreshEntity(String username, String refresh, Long expiredMs) {
    Date date = new Date(System.currentTimeMillis() + expiredMs);

    Refresh refreshEntity = new Refresh();
    refreshEntity.setUsername(username);
    refreshEntity.setRefreshToken(refresh);
    refreshEntity.setExpiration(String.valueOf(date));

    refreshReposiotry.save(refreshEntity);
  }
}
