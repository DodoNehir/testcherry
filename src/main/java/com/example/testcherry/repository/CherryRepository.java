package com.example.testcherry.repository;

import com.example.testcherry.domain.Cherry;
import java.util.List;
import java.util.Optional;

public interface CherryRepository {
  Cherry save(Cherry cherry);

  Optional<Cherry> findById(Long id);

  Optional<Cherry> findByName(String name);

  List<Cherry> findAll();

}
