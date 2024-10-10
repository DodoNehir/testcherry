package com.example.testcherry.controller;

import com.example.testcherry.domain.Response;
import com.example.testcherry.dto.MemberDto;
import com.example.testcherry.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/api/v1/members")
  public Response<MemberDto> createNewMember(@RequestBody MemberDto memberDto) {
    MemberDto newMemberDto = memberService.newMember(memberDto);
    return Response.success(newMemberDto);
  }

  @GetMapping("/api/v1/members/{id}")
  public Response<MemberDto> getMemberById(@PathVariable("id") Long id) {
    MemberDto memberDto = memberService.findMemberById(id);
    return Response.success(memberDto);
  }

  @PatchMapping("/api/v1/members/{id}")
  public Response<Void> updateMember(@PathVariable("id") Long id,
      @RequestBody MemberDto memberDto) {
    memberService.updateMemberInfo(id, memberDto);
    return Response.success(null);
  }

  @PatchMapping("/api/v1/members/delete/{id}")
  public Response<Void> deleteMember(@PathVariable("id") Long id) {
    memberService.deleteMemberById(id);
    return Response.success(null);
  }


}
