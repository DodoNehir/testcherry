package com.example.testcherry.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.testcherry.domain.product.service.ProductService;
import com.example.testcherry.exception.ProductNotFoundException;
import com.example.testcherry.domain.product.dto.ProductDto;
import com.example.testcherry.domain.product.entity.Product;
import com.example.testcherry.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @InjectMocks
  private ProductService productService;
  @Mock
  private ProductRepository productRepository;


  @Test
  void registerProduct() {
    // given
    ProductDto productDto = ProductDto.createTestProductDto();
    Product product = Product.of(productDto);

    when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

    //when
    ProductDto registered = productService.registerProduct(productDto);

    //then
    assertThat(registered.name()).isEqualTo(productDto.name());
  }


  @Test
  void findProductById_shouldReturnProductDto() {
    // given
    Long productId = 1L;
    Product product = Product.createTestProduct();
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // when
    ProductDto result = productService.findProductById(productId);

    // then
    assertThat(result.name()).isEqualTo(product.getName());
  }

  @Test
  void findProductById_shouldThrowException_whenProductNotFound() {
    // given
    Long productId = 1L;

    // when & then
    assertThrows(
        ProductNotFoundException.class, () -> productService.findProductById(productId));
  }



  @Test
  void findProductByName_shouldReturnEmptyList_whenNameIsEmpty() {
    // given
    String name = "";

    // when & then
    List<ProductDto> productDtoList = productService.findAllByNameContaining(name);

    // then
    assertThat(productDtoList).isEmpty();
  }

  @Test
  void updateProductById() {
    // given
    Long productId = 1L;
    Product existingProduct = Product.createTestProduct(10);
    ProductDto updateDto = ProductDto.createTestProductDto(20);

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

    // when
    productService.updateProductById(productId, updateDto);

    // then
    Product updatedProduct = productRepository.findById(productId).orElseThrow();
    assertThat(updatedProduct).isEqualTo(existingProduct);
  }

  @Test
  void deleteProductById() {
    // given
    Long productId = 1L;
    Product product = Product.createTestProduct();
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // when
    productService.deleteProductById(productId);

    // then
    verify(productRepository).delete(product);
  }
}