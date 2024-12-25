package com.example.testcherry.domain.refresh.service;

import com.example.testcherry.auth.jwt.exception.InvalidJwtException;
import com.example.testcherry.auth.jwt.util.JwtUtil;
import com.example.testcherry.domain.refresh.entity.Refresh;
import com.example.testcherry.domain.refresh.repository.RefreshReposiotry;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReissueService {

  private final JwtUtil jwtUtil;
  private final RefreshReposiotry refreshReposiotry;

  public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String refreshToken = null;
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("refreshToken")) {
        refreshToken = cookie.getValue();
      }
    }

    if (refreshToken == null) {
      throw new InvalidJwtException("refresh");
    }

    // expired 확인
    try {
      jwtUtil.isExpired(refreshToken);
    } catch (ExpiredJwtException e) {
      throw e;
    }

    // refresh 카테고리 확인
    String category = jwtUtil.getCategory(refreshToken);
    if (!category.equals("refresh")) {
      throw new InvalidJwtException("refresh");
    }

    // 저장되어 있는 refresh 확인
    boolean exists = refreshReposiotry.existsByRefreshToken(refreshToken);
    if (!exists) {
      throw new InvalidJwtException("refresh");
    }

    // 새로운 토큰 생성
    String username = jwtUtil.getUsername(refreshToken);
    String role = jwtUtil.getRole(refreshToken);

    String newAccessToken = jwtUtil.generateToken("access", username, role, 60 * 60 * 1000L);
    String newRefreshToken = jwtUtil.generateToken("refresh", username, role, 24 * 60 * 60 * 1000L);

    // 기존 refresh 토큰 삭제 & 새로운 refresh 토큰 추가
    refreshReposiotry.deleteByUsername(username);
    saveRefreshEntity(username, newRefreshToken, 24 * 60 * 60 * 1000L);

    String userAgent = request.getHeader("User-Agent");

    jwtUtil.setTokensInResponse(response, newAccessToken, newRefreshToken, userAgent);
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
