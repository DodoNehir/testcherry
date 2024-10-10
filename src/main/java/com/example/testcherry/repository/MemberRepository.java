package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import com.example.testcherry.dto.MemberDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberRepository {

  private final MemberJdbcRepository memberJdbcRepository;

  public MemberRepository(MemberJdbcRepository memberJdbcRepository) {
    this.memberJdbcRepository = memberJdbcRepository;
  }

  @Transactional
  public Member save(Member member) {
    return memberJdbcRepository.save(member);
  }

  @Transactional
  public Member findByName(String name) {
    return memberJdbcRepository.findByName(name)
        .orElseThrow(() -> new RuntimeException("Member not found"));
  }

  @Transactional
  public Member update(Long id, MemberDto memberDto) {
    Member member = getMemberById(id);
    member.update(memberDto);
    memberJdbcRepository.save(member);
    return member;
  }

  public Member getMemberById(Long id) {
    Member member = memberJdbcRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Member not found"));
    return member;
  }

  @Transactional
  public void delete(Long id) {
    Member member = getMemberById(id);
    member.delete();
    memberJdbcRepository.save(member);

  }

}
