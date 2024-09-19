package com.example.testcherry.controller;

import com.example.testcherry.service.MemberService;
import org.springframework.stereotype.Controller;

@Controller
public class CherryController {

  private MemberService cherryService;

  public CherryController(MemberService cherryService) {
    this.cherryService = cherryService;
  }


}
