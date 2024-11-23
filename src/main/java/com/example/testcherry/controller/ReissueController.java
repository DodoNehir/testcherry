package com.example.testcherry.controller;

import com.example.testcherry.model.entity.Response;
import com.example.testcherry.repository.RefreshReposiotry;
import com.example.testcherry.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
  private final RefreshReposiotry refreshReposiotry;

  @PostMapping("/reissue")
  public Response<Void> reissue(HttpServletRequest request, HttpServletResponse response) {
    response = reissueService.reissueAccessToken(request, response);

    return Response.success(null);
  }
}
