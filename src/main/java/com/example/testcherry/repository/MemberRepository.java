package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
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
    return memberJdbcRepository.findByName(name).orElseThrow(() -> new RuntimeException("Member not found"));
  }

  @Transactional
  public Member updateName(Long id , String newName) {
    Member member = getMemberById(id);
    member.updateName(newName);
    memberJdbcRepository.save(member);
    return member;
  }

  @Transactional
  public Member updateAddress(Long id , String newAddress) {
    Member member = getMemberById(id);
    member.updateAddress(newAddress);
    memberJdbcRepository.save(member);
    return member;
  }

  @Transactional
  public Member updatePhoneNumber(Long id , String newPhoneNumber) {
    Member member = getMemberById(id);
    member.updatePhoneNumber(newPhoneNumber);
    memberJdbcRepository.save(member);
    return member;
  }

  private Member getMemberById(Long id) {
    Member member = memberJdbcRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    return member;
  }

  @Transactional
  public void delete(Long id) {
    Member member = getMemberById(id);
    member.delete();
    memberJdbcRepository.save(member);

  }

}
