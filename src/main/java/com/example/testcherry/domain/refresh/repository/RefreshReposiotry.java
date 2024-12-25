package com.example.testcherry.domain.refresh.repository;

import com.example.testcherry.domain.refresh.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshReposiotry extends JpaRepository<Refresh, Long> {

  boolean existsByRefreshToken(String refreshToken);

  void deleteByUsername(String username);

}
