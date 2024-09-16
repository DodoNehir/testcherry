package com.example.testcherry.controller;

import com.example.testcherry.service.CherryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CherryController {
  private CherryService cherryService;

  public CherryController(CherryService cherryService) {
    this.cherryService = cherryService;
  }


}
