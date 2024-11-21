package com.example.testcherry.jwt;

import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.member.UserDetailsImpl;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private JwtUtil jwtUtil;

  public JwtFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String accessToken = request.getHeader("access");

    // 토큰 없으면 다음 필터로
    if (accessToken == null) {
//        || !accessToken.startsWith("Bearer ")) {

      filterChain.doFilter(request, response);
      return;
    }

    // token expired 면 401 응답
    try {
      jwtUtil.isExpired(accessToken);
    } catch (ExpiredJwtException e) {
      PrintWriter writer = response.getWriter();
      writer.println("access token expired");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String category = jwtUtil.getCategory(accessToken);

    // access token 아니면 401 응답
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
//    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // controller단에서 인증 정보를 사용할 수 있도록 SecurityContext에 인증 설정
    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
//    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//    SecurityContextHolder.setContext(securityContext);

    filterChain.doFilter(request, response);
  }
}
