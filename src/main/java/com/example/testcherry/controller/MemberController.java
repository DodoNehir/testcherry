package com.example.testcherry.controller;

import com.example.testcherry.domain.Response;
import com.example.testcherry.dto.MemberDto;
import com.example.testcherry.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/api/v1/member/new")
  public Response<MemberDto> createNewMember(@RequestBody MemberDto memberDto) {
    MemberDto newMemberDto = memberService.newMember(memberDto);
    return Response.success(newMemberDto);
  }
}
