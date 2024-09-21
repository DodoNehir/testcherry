package com.example.testcherry.service;

import com.example.testcherry.domain.Member;
import com.example.testcherry.repository.MemberRepository;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public Long join(Member member) throws SQLException {
    return memberRepository.save(member).getId();

  }

  public List<Member> findAll() {
    return memberRepository.findAll();
  }

}
