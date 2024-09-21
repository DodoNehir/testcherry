package com.example.testcherry.repository;

import com.example.testcherry.domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberRepository implements MemberRepository {

  // dataSource로부터 connection 을 가져온다. dataSource는 스프링이 주입해준다.
  private final DataSource dataSource;

  public JdbcMemberRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Member save(Member member) throws SQLException {
    String sql = "insert into member(name) values (?)";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      ps.setString(1, member.getName());

      ps.executeUpdate();
      rs = ps.getGeneratedKeys();

      if (rs.next()) {
        member.setId(rs.getLong(1));
      } else {
        throw new SQLException("Failed to insert member");
      }
      return member;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, ps, rs);
    }
  }

  @Override
  public Optional<Member> findById(Long id) {
    String sql = "select * from member where id = ?";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      ps = conn.prepareStatement(sql);
      ps.setLong(1, id);

      rs = ps.executeQuery();

      if (rs.next()) {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        return Optional.of(member);
      }

      return Optional.empty();

    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, ps, rs);
    }
  }

  @Override
  public Optional<Member> findByName(String name) {
    String sql = "select * from member where name = ?";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      ps = conn.prepareStatement(sql);
      ps.setString(1, name);

      rs = ps.executeQuery();

      if (rs.next()) {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        return Optional.of(member);
      }

      return Optional.empty();

    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, ps, rs);
    }
  }

  @Override
  public List<Member> findAll() {
    String sql = "select * from member";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      ps = conn.prepareStatement(sql);

      rs = ps.executeQuery();

      List<Member> members = new ArrayList<>();
      while (rs.next()) {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        members.add(member);
      }

      return members;

    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, ps, rs);
    }
  }


  private Connection getConnection() {
    return DataSourceUtils.getConnection(dataSource);
  }

  private void close(Connection conn) throws SQLException {
    DataSourceUtils.releaseConnection(conn, dataSource);
  }

  private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (ps != null) {
        ps.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (conn != null) {
        close(conn);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
