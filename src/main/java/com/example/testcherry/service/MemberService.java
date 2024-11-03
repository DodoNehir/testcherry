package com.example.testcherry.service;

import com.example.testcherry.exception.MemberNotFoundException;
import com.example.testcherry.model.dto.MemberDto;
import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.member.LoginRequestBody;
import com.example.testcherry.model.member.MemberAuthenticationResponse;
import com.example.testcherry.repository.MemberRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtService jwtService;

  public MemberService(MemberRepository memberRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      JwtService jwtService) {
    this.memberRepository = memberRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtService = jwtService;
  }

  public boolean isPresentUsername(String username) {
    return memberRepository.findByUsername(username).isPresent();
  }

  public MemberDto newMember(MemberDto memberDto) {
    Member savedMember = memberRepository
        .save(new Member(memberDto.username(),
            bCryptPasswordEncoder.encode(memberDto.password()),
            memberDto.address(),
            memberDto.phoneNumber()));
    return MemberDto.from(savedMember);
  }

  public MemberDto findMemberById(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException(id));
    return MemberDto.from(member);
  }

  public UserDetails loadMemberByUsername(String username) {
    return memberRepository.findByUsername(username)
        .orElseThrow(() -> new MemberNotFoundException(username));
  }

  public void updateMemberInfo(Long id, MemberDto memberDto) {
    memberRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException(id));
    memberRepository.save(Member.of(memberDto));
  }

  public void deleteMemberById(Long id) {
    memberRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException(id));
  }


  public MemberAuthenticationResponse authenticate(LoginRequestBody loginRequestBody) {
    String username = loginRequestBody.username();
    String password = loginRequestBody.password();

    // id
    Member member = memberRepository.findByUsername(username)
        .orElseThrow(() -> new MemberNotFoundException(username));

    // password
    if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
      throw new MemberNotFoundException(username);
    }

    String toekn = jwtService.generateAccessToken(member);

    return new MemberAuthenticationResponse(toekn);
  }
}
