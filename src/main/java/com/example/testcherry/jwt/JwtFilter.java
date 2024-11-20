package com.example.testcherry.jwt;

import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.member.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    String authHeader = request.getHeader("Authorization");

    // Authorization 헤더 검증
    if (authHeader == null
        || !authHeader.startsWith("Bearer ")) {
//        || securityContext.getAuthentication() != null

      filterChain.doFilter(request, response);
      return;
    }

    String accessToken = authHeader.substring(7);

    // token 유효시간 검증
    if (jwtUtil.isExpired(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    String username = jwtUtil.getUsername(accessToken);
    String role = jwtUtil.getRole(accessToken);

    // TODO ? entity 임시 생성?
    Member member = new Member(username, role);

    UserDetails userDetails = new UserDetailsImpl(member);
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
