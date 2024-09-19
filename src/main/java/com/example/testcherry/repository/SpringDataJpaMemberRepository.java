package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {


  Optional<Member> findByName(String name);

}
