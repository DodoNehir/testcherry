package com.example.testcherry.config;

import com.example.testcherry.service.JwtService;
import com.example.testcherry.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private JwtService jwtService;
  private MemberService memberService;

  public JwtAuthenticationFilter(JwtService jwtService, MemberService memberService) {
    this.jwtService = jwtService;
    this.memberService = memberService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    SecurityContext securityContext = SecurityContextHolder.getContext();
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null
        && authHeader.startsWith("Bearer ")
        && securityContext.getAuthentication() == null) {

      String accessToken = authHeader.substring(7);
      String username = jwtService.getUsername(accessToken);
      UserDetails member = memberService.loadMemberByUsername(username);

      // controller단에서 인증 정보를 사용할 수 있도록 SecurityContext에 인증 설정
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          member, null, member.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      securityContext.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(securityContext);
    }

    filterChain.doFilter(request, response);
  }
}
