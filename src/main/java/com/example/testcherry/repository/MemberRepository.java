package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import com.example.testcherry.dto.MemberDto;
import com.example.testcherry.exception.MemberNotFoundException;
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
        .orElseThrow(() -> new MemberNotFoundException("Member with name " + name + " not found"));
  }

  @Transactional
  public Member update(Long id, MemberDto memberDto) {
    Member member = findById(id);
    member.update(memberDto);
    memberJdbcRepository.save(member);
    return member;
  }

  public Member findById(Long id) {
    Member member = memberJdbcRepository.findById(id)
        .orElseThrow(() -> new MemberNotFoundException("Member with id " + id + " not found"));
    return member;
  }

  @Transactional
  public void delete(Long id) {
    Member member = findById(id);
    member.delete();
    memberJdbcRepository.save(member);

  }

}
