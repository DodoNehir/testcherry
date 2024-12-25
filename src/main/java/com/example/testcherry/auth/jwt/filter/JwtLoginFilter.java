package com.example.testcherry.auth.jwt.filter;

import com.example.testcherry.auth.jwt.util.JwtUtil;
import com.example.testcherry.domain.member.UserDetailsImpl;
import com.example.testcherry.domain.member.dto.LoginRequestBody;
import com.example.testcherry.domain.refresh.entity.Refresh;
import com.example.testcherry.domain.refresh.repository.RefreshReposiotry;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final RefreshReposiotry refreshReposiotry;

  public JwtLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
      RefreshReposiotry refreshReposiotry) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.refreshReposiotry = refreshReposiotry;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    // form-data 형식
//    String username = obtainUsername(request);
//    String password = obtainPassword(request);

    // raw (JSON) 형식
    LoginRequestBody loginRequestBody;

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ServletInputStream inputStream = request.getInputStream();
      String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
      loginRequestBody = objectMapper.readValue(messageBody, LoginRequestBody.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String username = loginRequestBody.username();
    String password = loginRequestBody.password();

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        username, password, null);

    return authenticationManager.authenticate(authToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) throws IOException, ServletException {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String username = userDetails.getUsername();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    String accessToken = jwtUtil.generateToken("access", username, role, 60 * 60 * 1000L);
    String refreshToken = jwtUtil.generateToken("refresh", username, role, 24 * 60 * 60 * 1000L);

    saveRefreshEntity(username, refreshToken, 24 * 60 * 60 * 1000L);

    // User Agent 에 따라 응답 바꾸기
    String userAgent = request.getHeader("User-Agent");

    jwtUtil.setTokensInResponse(response, accessToken, refreshToken, userAgent);

    response.setStatus(HttpStatus.OK.value());
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    //로그인 실패시 401 응답 코드 반환
    response.getWriter().write("login failed: " + failed.getMessage());
    response.setStatus(401);
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
