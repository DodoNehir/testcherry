package com.example.testcherry.auth.handler;

import com.example.testcherry.auth.jwt.util.JwtUtil;
import com.example.testcherry.domain.member.UserDetailsImpl;
import com.example.testcherry.domain.refresh.entity.Refresh;
import com.example.testcherry.domain.refresh.repository.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final RefreshRepository refreshRepository;
  private final Long accessTokenExpiresIn = 60 * 60 * 1000L;
  private final Long refreshExpireTime = 24 * 60 * 60 * 1000L;

  public CustomAuthenticationSuccessHandler(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

//    Member principal = (Member) authentication.getPrincipal(); // 인증된 사용자 정보 획득
//    String token = jwtUtil.generateToken(principal); // JWT 생성

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String username = userDetails.getUsername();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    String accessToken = jwtUtil.generateToken("access", username, role, accessTokenExpiresIn);
    String refreshToken = jwtUtil.generateToken("refresh", username, role, refreshExpireTime);

    saveRefreshEntity(username, refreshToken);

    // User Agent 에 따라 응답 바꾸기
    String userAgent = request.getHeader("User-Agent");

    jwtUtil.setTokensInResponse(response, accessToken, refreshToken, userAgent);

    response.setStatus(HttpServletResponse.SC_OK);

  }

  private void saveRefreshEntity(String username, String refresh) {
    Date date = new Date(System.currentTimeMillis() + refreshExpireTime);

    Refresh refreshEntity = new Refresh();
    refreshEntity.setUsername(username);
    refreshEntity.setRefreshToken(refresh);
    refreshEntity.setExpiration(String.valueOf(date));

    refreshRepository.save(refreshEntity);
  }
}
