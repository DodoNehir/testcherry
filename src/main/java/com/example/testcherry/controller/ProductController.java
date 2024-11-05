package com.example.testcherry.controller;

import com.example.testcherry.model.dto.ProductDto;
import com.example.testcherry.model.entity.Response;
import com.example.testcherry.service.ProductService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/all")
  public Response<List<ProductDto>> getAllProducts() {

    List<ProductDto> productDtoList = productService.findAll();
    return Response.success(productDtoList);
  }

  @GetMapping("/{id}")
  public Response<ProductDto> getProductById(@RequestParam("id") Long id) {

    ProductDto productDto = productService.findProductById(id);
    return Response.success(productDto);
  }

  @GetMapping("/name/{name}")
  public Response<ProductDto> getProductByName(@RequestParam("name") String name) {

    ProductDto productDto = productService.findProductByName(name);
    return Response.success(productDto);
  }

  @PatchMapping("/{id}")
  public Response<Void> updateProduct(@PathVariable("id") Long id,
      @RequestBody ProductDto productDto,
      Authentication authentication) {

    productService.updateProductById(id, productDto);
    return Response.success(null);
  }

  @DeleteMapping("/{id}")
  public Response<Void> deleteProduct(@PathVariable("id") Long id,
      Authentication authentication) {

    productService.deleteProductById(id);
    return Response.success(null);
  }

}
