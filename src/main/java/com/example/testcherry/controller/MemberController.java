package com.example.testcherry.controller;

import com.example.testcherry.model.entity.Response;
import com.example.testcherry.model.dto.MemberDto;
import com.example.testcherry.model.member.CheckRole;
import com.example.testcherry.model.member.LoginRequestBody;
import com.example.testcherry.model.member.MemberAuthenticationResponse;
import com.example.testcherry.model.member.Role;
import com.example.testcherry.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
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
  @PostMapping
  public Response<MemberDto> signUp(@Valid @RequestBody MemberDto memberDto) {
    MemberDto newMemberDto = memberService.newMember(memberDto);
    return Response.success(newMemberDto);
  }

  @Operation(summary = "인증", description = "가입된 멤버가 맞는 지 확인합니다.")
  @PostMapping("/authenticate")
  public Response<MemberAuthenticationResponse> authenticate(@Valid @RequestBody LoginRequestBody loginRequestBody) {
    MemberAuthenticationResponse authenticationResponse = memberService.authenticate(loginRequestBody);
    return Response.success(authenticationResponse);
  }

//  @PostMapping
//  public Response<MemberDto> signIn(@Valid @RequestBody MemberDto memberDto) {
//    MemberDto newMemberDto = memberService.newMember(memberDto);
//    return Response.success(newMemberDto);
//  }

  @CheckRole(roles = Role.ADMIN)
  @Operation(summary = "id로 회원찾기", description = "id는 회원의 순서를 말합니다. ADMIN 만 사용할 수 있습니다.")
  @GetMapping("/{id}")
  public Response<MemberDto> getMemberById(@PathVariable("id") Long id) {
    MemberDto memberDto = memberService.findMemberById(id);
    return Response.success(memberDto);
  }

  @CheckRole(roles = Role.MEMBER)
  @Operation(summary = "회원정보수정", description = "본인만 정보를 수정할 수 있습니다.")
  @PatchMapping("/{id}")
  public Response<Void> updateMember(@PathVariable("id") Long id,
      @RequestBody MemberDto memberDto,
      Authentication authentication) {
    memberService.updateMemberInfo(id, memberDto);
    return Response.success(null);
  }

  @CheckRole(roles = Role.MEMBER)
  @Operation(summary = "회원탈퇴", description = "본인만 탈퇴할 수 있습니다.")
  @DeleteMapping("/{id}")
  public Response<Void> deleteMember(@PathVariable("id") Long id,
      Authentication authentication) {
    memberService.deleteMemberById(id);
    return Response.success(null);
  }


}
