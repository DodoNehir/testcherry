package com.example.testcherry.interceptor;

import com.example.testcherry.exception.ForbiddenException;
import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.member.CheckRole;
import com.example.testcherry.model.member.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class CheckRoleInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // Controller 에 있는 메서드인지 확인하기 위해 HandlerMethod 타입인지 체크
    if(!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;

    //@checkRole 이 부착되지 않았다면 true 리턴
    CheckRole checkRole = handlerMethod.getMethodAnnotation(CheckRole.class);
    if(checkRole == null) {
      return true;
    }

    // 로그인 하지 않았다면 false 리턴
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!authentication.isAuthenticated()) {
      return false;
    }

    Member authMember = (Member)authentication.getPrincipal();

    // admin 이면 무조건 통과
    if (authMember.getRole()== Role.ADMIN) {
      return true;
    }

    // 로그인한 유저의 Role 이 요구되는 role 중 포함되는지 확인
    if (ArrayUtils.contains(checkRole.roles(), authMember.getRole())) {
      return true;
    } else {
      throw new ForbiddenException();
    }

  }

}
