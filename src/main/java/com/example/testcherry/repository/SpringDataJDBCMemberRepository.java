package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface SpringDataJDBCMemberRepository extends CrudRepository<Member, Long> {

}
