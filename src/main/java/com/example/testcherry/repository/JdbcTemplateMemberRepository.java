package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcTemplateMemberRepository implements MemberRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateMemberRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Member save(Member member) {
//    String sql = "insert into member(name) values (?)";
//    jdbcTemplate.update(sql, member.getName());
    SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate);
    insert.withTableName("members").usingGeneratedKeyColumns("id");

    Map<String, Object> params = new HashMap<>();
    params.put("name", member.getName());

    Number key = insert.executeAndReturnKey(new MapSqlParameterSource(params));
    member.setMemberId(key.longValue());
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    String sql = "select * from members where id = ?";
    List<Member> members = jdbcTemplate.query(sql, memberRowMapper, id);

    return Optional.ofNullable(members.get(0));
  }

  @Override
  public Optional<Member> findByName(String name) {
    String sql = "select * from members where name = ?";
    List<Member> members = jdbcTemplate.query(sql, memberRowMapper, name);

    return Optional.ofNullable(members.get(0));
  }

  @Override
  public List<Member> findAll() {
    String sql = "select * from members";
    return jdbcTemplate.query(sql, memberRowMapper);
  }


  private RowMapper<Member> memberRowMapper = (resultSet, rowNum) -> {
    Member member = new Member();
    member.setMemberId(resultSet.getLong("id"));
    member.setName(resultSet.getString("name"));
    return member;
  };
}
