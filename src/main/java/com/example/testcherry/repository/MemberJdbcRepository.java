package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MemberJdbcRepository extends CrudRepository<Member, Long> {
  Optional<Member> findByName(String name);

}
