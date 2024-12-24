package com.example.testcherry.repository;

import com.example.testcherry.model.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshReposiotry extends JpaRepository<Refresh, Long> {

  boolean existsByRefreshToken(String refreshToken);

  void deleteByUsername(String username);

}
