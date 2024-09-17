package com.example.testcherry.controller;

import com.example.testcherry.service.CherryService;
import java.lang.reflect.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CherryController {
  private CherryService cherryService;

  public CherryController(CherryService cherryService) {
    this.cherryService = cherryService;
  }

  @GetMapping("/members/new")
  public String createForm() {
    return "members/createMemberForm.html";
  }

//  @PostMapping("/members/new")
//  public String create(MemberForm form) {
//    Member member = new Member();
//    member.setName(form.getName());
//
//    cherryService.join(member);
//
//    return "redirect:/";
//
//  }

}
