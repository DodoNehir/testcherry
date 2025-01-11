package com.example.testcherry.domain.member.service;

import com.example.testcherry.auth.jwt.util.JwtUtil;
import com.example.testcherry.domain.member.dto.CheckIdResult;
import com.example.testcherry.domain.member.dto.JoinRequestBody;
import com.example.testcherry.domain.member.dto.MemberDeleteRequest;
import com.example.testcherry.domain.member.dto.MemberDto;
import com.example.testcherry.domain.member.entity.Member;
import com.example.testcherry.domain.member.repository.MemberRepository;
import com.example.testcherry.domain.refresh.repository.RefreshRepository;
import com.example.testcherry.exception.ForbiddenException;
import com.example.testcherry.exception.MemberAlreadyExistsException;
import com.example.testcherry.exception.MemberNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

  private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtUtil jwtUtil;
  private final RefreshRepository refreshRepository;

  public MemberService(MemberRepository memberRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil,
      RefreshRepository refreshRepository) {
    this.memberRepository = memberRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
  }


  public String join(JoinRequestBody joinRequestBody) {
    String username = joinRequestBody.username();

    if (memberRepository.existsByUsername(username)) {
      throw new MemberAlreadyExistsException(username);
    }

    memberRepository.save(
        new Member(joinRequestBody.username(),
            bCryptPasswordEncoder.encode(joinRequestBody.password()),
            joinRequestBody.address(),
            joinRequestBody.phoneNumber()));
    return username;
  }

  public MemberDto findMemberById(Long id) {
    logger.info("Find member by id: {}", id);
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException(id));
    return MemberDto.from(member);
  }

  public CheckIdResult isUsernameExist(String username) {
    if (Objects.equals(username, "0") | Objects.equals(username, "undefined")
        | Objects.equals(username, "null") | Objects.equals(username, null)
        | Objects.equals(username, "select") | Objects.equals(username, "delete")
        | Objects.equals(username, "create") | Objects.equals(username, "update")) {
      return new CheckIdResult(false, "잘못된 아이디 형식입니다.");
    }

    if (memberRepository.existsByUsername(username)) {
      return new CheckIdResult(false, "중복된 아이디입니다.");
    } else {
      return new CheckIdResult(true, "사용 가능한 아이디입니다.");
    }
  }

  public Member findMemberByUsername(String username) {
    logger.info("Find member by username: {}", username);
    return memberRepository.findByUsername(username)
        .orElseThrow(() -> new MemberNotFoundException(username));
  }

  public void updateMemberInfo(Member member, JoinRequestBody updateRequestBody) {
    logger.info("Update member info");
    if (bCryptPasswordEncoder.matches(updateRequestBody.password(), member.getPassword())) {
      member.updateInfo(updateRequestBody);
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
    refreshRepository.deleteByUsername(username);

    // set refresh cookie null
    Cookie cookie = new Cookie("refresh", null);
    cookie.setMaxAge(0);
    cookie.setSecure(true); // HTTPS
    cookie.setPath("/");
    response.addCookie(cookie);
  }


}
