package com.example.testcherry.controller;

import com.example.testcherry.model.dto.ProductDto;
import com.example.testcherry.model.entity.Response;
import com.example.testcherry.model.member.CheckRole;
import com.example.testcherry.model.member.Role;
import com.example.testcherry.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product", description = "Product API")
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @CheckRole(roles = Role.ADMIN)
  @Operation(summary = "상품 등록", description = "ADMIN 계정만 상품을 등록할 수 있습니다.")
  @PostMapping
  public Response<ProductDto> registerProduct(@Valid @RequestBody ProductDto productDto) {

    ProductDto registeredProductDto = productService.registerProduct(productDto);
    return Response.success(registeredProductDto);
  }

  @Operation(summary = "모든 상품 조회", description = "등록된 상품을 모두 조회합니다. 모든 사용자가 사용가능합니다.")
  @GetMapping("/all")
  public Response<List<ProductDto>> getAllProducts() {

    List<ProductDto> productDtoList = productService.findAll();
    return Response.success(productDtoList);
  }

  @Operation(summary = "id로 상품 조회", description = "등록된 상품을 id로 조회합니다. 모든 사용자가 사용가능합니다.")
  @GetMapping("/{id}")
  public Response<ProductDto> getProductById(@PathVariable("id") Long id) {

    ProductDto productDto = productService.findProductById(id);
    return Response.success(productDto);
  }

  @Operation(summary = "이름으로 상품 조회", description = "등록된 상품을 상품 이름으로 조회합니다. 모든 사용자가 사용가능합니다.")
  @GetMapping("/name/{name}")
  public Response<List<ProductDto>> getProductByName(@PathVariable("name") String name) {

    List<ProductDto> productDtoList = productService.findAllByNameContaining(name);
    return Response.success(productDtoList);
  }

  @CheckRole(roles = Role.ADMIN)
  @Operation(summary = "상품 정보 업데이트", description = "ADMIN 계정만 상품 정보를 수정할 수 있습니다.")
  @PatchMapping("/{id}")
  public Response<Void> updateProduct(@PathVariable("id") Long id,
      @RequestBody ProductDto productDto) {

    productService.updateProductById(id, productDto);
    return Response.success(null);
  }

  @CheckRole(roles = Role.ADMIN)
  @Operation(summary = "상품 정보 삭제", description = "ADMIN 계정만 상품을 삭제할 수 있습니다.")
  @DeleteMapping("/{id}")
  public Response<Void> deleteProduct(@PathVariable("id") Long id) {

    productService.deleteProductById(id);
    return Response.success(null);
  }

}
