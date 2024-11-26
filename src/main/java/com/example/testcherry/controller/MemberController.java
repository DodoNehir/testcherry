package com.example.testcherry.controller;

import com.example.testcherry.model.dto.MemberDto;
import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.entity.Response;
import com.example.testcherry.model.member.MemberDeleteRequest;
import com.example.testcherry.model.member.UserDetailsImpl;
import com.example.testcherry.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "Member API")
@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @Operation(summary = "회원가입", description = "누구나 가입할 수 있습니다.")
  @PostMapping("/join")
  public Response<String> join(@Valid @RequestBody MemberDto memberDto) {
    String newMemberUsername = memberService.join(memberDto);
    return Response.success(newMemberUsername);
  }

  @Operation(summary = "로그아웃")
  @PostMapping("/logout")
  public Response<String> logout(@CookieValue("refresh") String refresh, HttpServletResponse response) {
    memberService.logout(refresh, response);
    return Response.success("logout successful");
  }

//  @Operation(summary = "인증", description = "가입된 멤버가 맞는 지 확인합니다.")
//  @PostMapping("/login")
//  public Response<Void> login(
//      @Valid @RequestBody LoginRequestBody loginRequestBody) {
//    memberService.login(
//        loginRequestBody);
//    return Response.success(null);
//  }


  @Operation(summary = "id로 회원찾기", description = "id는 회원의 순서를 말합니다. ADMIN 만 사용할 수 있습니다.")
  @GetMapping("/{id}")
  public Response<MemberDto> getMemberById(@PathVariable("id") Long id) {
    MemberDto memberDto = memberService.findMemberById(id);
    return Response.success(memberDto);
  }

  @Operation(summary = "회원정보수정", description = "본인만 정보를 수정할 수 있습니다.")
  @PatchMapping("/update")
  public Response<Void> updateMember(@RequestBody MemberDto updateMemberDto,
      Authentication authentication) {

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    Member member = memberService.findMemberByUsername(userDetails.getUsername());
    memberService.updateMemberInfo(member, updateMemberDto);
    return Response.success(null);
  }

  @Operation(summary = "회원탈퇴", description = "본인만 탈퇴할 수 있습니다.")
  @DeleteMapping("/delete")
  public Response<Void> deleteMember(@RequestBody MemberDeleteRequest request,
      Authentication authentication) {

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    Member member = memberService.findMemberByUsername(userDetails.getUsername());
    memberService.deleteMemberByUsername(member, request);
    return Response.success(null);
  }


}
