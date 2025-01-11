package com.example.testcherry.controller;

import com.example.testcherry.domain.member.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  @GetMapping("/")
  public String homeController(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()
        && !authentication.getPrincipal().equals("anonymousUser")) {
      // 사용자가 인증된 경우
      UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
      model.addAttribute("username", user.getUsername());
      model.addAttribute("isAuthenticated", true);
    } else {
      model.addAttribute("isAuthenticated", false);
    }
    return "home";
  }

  @GetMapping("members/signup")
  public String signupForm() {
    return "members/signup";
  }

  @GetMapping("/login")
  public String loginForm(Model model,
      @RequestParam(value = "error", required = false) String error) {
    if (error != null) {
      model.addAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
    }
    return "login";
  }

}
