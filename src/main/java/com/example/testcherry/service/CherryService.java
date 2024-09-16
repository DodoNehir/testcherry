package com.example.testcherry.service;

import com.example.testcherry.repository.CherryRepository;
import org.springframework.stereotype.Service;

@Service
public class CherryService {
  private final CherryRepository cherryRepository;

  public CherryService(CherryRepository cherryRepository) {
    this.cherryRepository = cherryRepository;
  }
}
