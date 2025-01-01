package com.example.testcherry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  @GetMapping("/")
  public String homeController() {
    return "home";
  }

  @GetMapping("members/signup")
  public String signupForm() {
    return "members/signup";
  }

  @GetMapping("members/login")
  public String loginForm(Model model, @RequestParam(value = "error", required = false) String error) {
    if (error != null) {
      model.addAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
    }
    return "members/login";
  }

}
