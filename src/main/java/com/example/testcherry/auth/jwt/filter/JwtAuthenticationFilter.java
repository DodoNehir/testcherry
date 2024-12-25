package com.example.testcherry.auth.jwt.filter;

import com.example.testcherry.auth.jwt.util.JwtUtil;
import com.example.testcherry.domain.member.entity.Member;
import com.example.testcherry.domain.member.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String bearerToken = request.getHeader("Authorization");

    // 토큰 없거나 Bearer 토큰이 아니면 다음 필터로
    if (bearerToken == null || !bearerToken.toLowerCase().startsWith("bearer ")) {

      filterChain.doFilter(request, response);
      return;
    }

    String accessToken  = bearerToken.substring(7);

    // expired 체크
    try {
      jwtUtil.isExpired(accessToken);
    } catch (ExpiredJwtException e) {
      PrintWriter writer = response.getWriter();
      writer.println("access token expired");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    // 카테고리 access 인지 체크
    String category = jwtUtil.getCategory(accessToken);
    if (!category.equals("access")) {
      PrintWriter writer = response.getWriter();
      writer.println("invalid access token");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String username = jwtUtil.getUsername(accessToken);
    String role = jwtUtil.getRole(accessToken);

    // entity 임시 생성
    Member member = new Member(username, role);

    UserDetailsImpl userDetails = new UserDetailsImpl(member);

    // controller단에서 인증 정보를 사용할 수 있도록 SecurityContext에 인증 설정
    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
//    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//    SecurityContextHolder.setContext(securityContext);

    filterChain.doFilter(request, response);
  }
}
