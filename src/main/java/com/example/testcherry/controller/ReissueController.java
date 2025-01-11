package com.example.testcherry.controller;

import com.example.testcherry.domain.refresh.repository.RefreshRepository;
import com.example.testcherry.domain.refresh.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JWT 받아서 검증하고 응답해주는 컨트롤러
 **/

@RequiredArgsConstructor
@RestController
public class ReissueController {

  private final ReissueService reissueService;
//  private final RefreshRepository refreshRepository;

  @PostMapping("/reissue")
  public void reissue(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    reissueService.reissueAccessToken(request, response);
  }
}
