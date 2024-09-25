package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

}
