package com.example.testcherry.service;

import com.example.testcherry.domain.Member;
import com.example.testcherry.repository.SpringDataJpaMemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final SpringDataJpaMemberRepository springDataJpaMemberRepository;

  public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository) {
    this.springDataJpaMemberRepository = springDataJpaMemberRepository;
  }

  public Long join(Member member) {
    return springDataJpaMemberRepository.save(member).getId();

  }

  public List<Member> findAll() {
    return springDataJpaMemberRepository.findAll();
  }

}
