package com.example.testcherry.repository;

import com.example.testcherry.domain.Cherry;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryCherryRepository implements CherryRepository{

  @Override
  public Cherry save(Cherry cherry) {
    return null;
  }

  @Override
  public Optional<Cherry> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public Optional<Cherry> findByName(String name) {
    return Optional.empty();
  }

  @Override
  public List<Cherry> findAll() {
    return List.of();
  }
}
