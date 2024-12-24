package com.example.testcherry.service;

import com.example.testcherry.exception.ForbiddenException;
import com.example.testcherry.exception.InvalidJwtException;
import com.example.testcherry.exception.MemberAlreadyExistsException;
import com.example.testcherry.exception.MemberNotFoundException;
import com.example.testcherry.jwt.JwtUtil;
import com.example.testcherry.model.dto.MemberDto;
import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.member.MemberDeleteRequest;
import com.example.testcherry.repository.MemberRepository;
import com.example.testcherry.repository.RefreshReposiotry;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtUtil jwtUtil;
  private final RefreshReposiotry refreshReposiotry;

  public MemberService(MemberRepository memberRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil,
      RefreshReposiotry refreshReposiotry) {
    this.memberRepository = memberRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtUtil = jwtUtil;
    this.refreshReposiotry = refreshReposiotry;
  }


  public String join(MemberDto memberDto) {
    String username = memberDto.username();

    if (memberRepository.existsByUsername(username)) {
      throw new MemberAlreadyExistsException(username);
    }

    memberRepository.save(
        new Member(memberDto.username(),
            bCryptPasswordEncoder.encode(memberDto.password()),
            memberDto.address(),
            memberDto.phoneNumber()));
    return username;
  }

  public MemberDto findMemberById(Long id) {
    logger.info("Find member by id: {}", id);
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException(id));
    return MemberDto.from(member);
  }

  public Member findMemberByUsername(String username) {
    logger.info("Find member by username: {}", username);
    return memberRepository.findByUsername(username)
        .orElseThrow(() -> new MemberNotFoundException(username));
  }

  public void updateMemberInfo(Member member, MemberDto updateMemberDto) {
    logger.info("Update member info: {}", updateMemberDto);
    if (bCryptPasswordEncoder.matches(updateMemberDto.password(), member.getPassword())) {
      member.update(updateMemberDto);
      memberRepository.save(member);
    } else {
      throw new ForbiddenException();
    }
  }

  public void deleteMemberByUsername(Member member, MemberDeleteRequest request) {
    logger.info("Delete member by username: {}", request);
    if (bCryptPasswordEncoder.matches(request.password(), member.getPassword())) {
      member.delete();
      memberRepository.save(member);
    } else {
      throw new ForbiddenException();
    }
  }

  public void logout(String username, HttpServletResponse response) {
    // delete refreshToken from DB
    refreshReposiotry.deleteByUsername(username);

    // set refresh cookie null
    Cookie cookie = new Cookie("refresh", null);
    cookie.setMaxAge(0);
//    cookie.setSecure(true); // HTTPS
    cookie.setPath("/");
    response.addCookie(cookie);
  }


}
