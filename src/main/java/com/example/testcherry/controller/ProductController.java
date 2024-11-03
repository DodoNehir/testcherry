package com.example.testcherry.controller;

import com.example.testcherry.model.dto.ProductDto;
import com.example.testcherry.model.entity.Response;
import com.example.testcherry.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public Response<ProductDto> registerProduct(@RequestBody ProductDto productDto,
      Authentication authentication) {

    // TODO: 권한 있는 사람만 등록할 수 있도록 확인해야 함
//    authentication.getAuthorities()

    ProductDto registeredProductDto = productService.registerProduct(productDto);
    return Response.success(registeredProductDto);
  }

}
