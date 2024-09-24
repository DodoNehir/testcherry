package com.example.testcherry.controller;

import com.example.testcherry.domain.Member;
import com.example.testcherry.service.MemberService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  private final MemberService memberService;

  public HomeController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/members/new")
  public String createForm() {
    return "members/createMemberForm.html";
  }

  @PostMapping("/members/new")
  public String signUp(
      @RequestParam String name
  ) throws SQLException {
    Member member = new Member();
    member.setName(name);
    member.setId(1L);

    memberService.join(member);

    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    List<Member> members = memberService.findAll();

    // member list 자체를 Model 에 담는다.
    model.addAttribute("members", members);

    return "/members/memberList";
  }

}
