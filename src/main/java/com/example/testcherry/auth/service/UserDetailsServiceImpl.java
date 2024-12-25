package com.example.testcherry.auth.service;

import com.example.testcherry.domain.member.UserDetailsImpl;
import com.example.testcherry.domain.member.entity.Member;
import com.example.testcherry.domain.member.repository.MemberRepository;
import com.example.testcherry.exception.MemberNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberRepository memberRepository;

  public UserDetailsServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByUsername(username)
        .orElseThrow(() -> new MemberNotFoundException(username));

    return new UserDetailsImpl(member);
  }
}
