package com.example.testcherry.domain.member.repository;

import com.example.testcherry.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  boolean existsByUsername(String username);

  Optional<Member> findByUsername(String username);

}
