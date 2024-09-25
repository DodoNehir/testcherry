package com.example.testcherry.service;

import com.example.testcherry.domain.Member;
import com.example.testcherry.dto.MemberDto;
import com.example.testcherry.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Transactional
  public MemberDto newMember(MemberDto memberDto) {
    Member savedMember = memberRepository.save(Member.of(memberDto));
    return MemberDto.from(savedMember);
  }


}
