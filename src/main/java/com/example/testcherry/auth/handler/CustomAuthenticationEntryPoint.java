package com.example.testcherry.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 로그인을 하지 않은 사용자가 로그인 유저를 위한 API 로 접근할 떄
 * 또는 잘못된 ID/PW 입력 시
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String json = String.format("{\"code\": %d, \"message\": \"%s\"}",
        HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자입니다.");
    response.getWriter().write(json);
  }
}
