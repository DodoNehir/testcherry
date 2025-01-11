package com.example.testcherry.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.testcherry.AbstractIntegrationTest;
import com.example.testcherry.domain.product.entity.Product;
import com.example.testcherry.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@Transactional
class ProductControllerTest extends AbstractIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProductRepository productRepository;

  private Product product;


  @BeforeEach
  void setUp() {
    product = Product.createTestProduct();
    product = productRepository.save(product);
  }

  // CREATE
  @Test
  @WithMockUser
  void registerProductWithAnonymousRole() throws Exception {
    mockMvc.perform(post("/products")
            .contentType("application/json")
            .content("{\n"
                + "    \"name\": \"Apple\",\n"
                + "    \"description\": \"Silicon\",\n"
                + "    \"quantity\": \"100\"\n"
                + "}"))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "MEMBER")
  void registerProductWithMemberRole() throws Exception {
    mockMvc.perform(post("/products")
            .contentType("application/json")
            .content("{\n"
                + "    \"name\": \"Apple\",\n"
                + "    \"description\": \"Silicon\",\n"
                + "    \"quantity\": \"100\"\n"
                + "}"))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void registerProductWithAdminRole() throws Exception {
    mockMvc.perform(post("/products")
            .contentType("application/json")
            .content("{\n"
                + "    \"name\": \"Apple\",\n"
                + "    \"description\": \"Silicon\",\n"
                + "    \"quantity\": \"100\"\n"
                + "}"))
        .andExpect(status().isOk());
  }


  // READ
  @Test
  @WithMockUser
  void getAllProductsWithoutRole() throws Exception {
    mockMvc.perform(get("/products/all"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "MEMBER")
  void getAllProductsWithMemberRole() throws Exception {
    mockMvc.perform(get("/products/all"))
        .andExpect(status().isOk());
  }


  // UPDATE
  @Test
  @WithMockUser
  void updateProductWithAnonymousRole() throws Exception {
    mockMvc.perform(patch("/products/" + product.getProductId())
            .contentType("application/json")
            .content("{\n"
                + "    \"name\": \"Apple\",\n"
                + "    \"description\": \"Silicon\",\n"
                + "    \"quantity\": \"100\"\n"
                + "}"))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "MEMBER")
  void updateProductWithMemberRole() throws Exception {
    mockMvc.perform(patch("/products/" + product.getProductId())
            .contentType("application/json")
            .content("{\n"
                + "    \"name\": \"Apple\",\n"
                + "    \"description\": \"Silicon\",\n"
                + "    \"quantity\": \"100\"\n"
                + "}"))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateProductWithAdminRole() throws Exception {
    mockMvc.perform(patch("/products/" + product.getProductId())
            .contentType("application/json")
            .content("{\n"
                + "    \"name\": \"Apple\",\n"
                + "    \"description\": \"Silicon\",\n"
                + "    \"quantity\": \"100\"\n"
                + "}"))
        .andExpect(status().isOk());
  }


  // DELETE
  @Test
  @WithMockUser
  void deleteProductWithAnonymousRole() throws Exception {
    mockMvc.perform(delete("/products/" + product.getProductId()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "MEMBER")
  void deleteProductWithMemberRole() throws Exception {
    mockMvc.perform(delete("/products/" + product.getProductId()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteProductWithAdminRole() throws Exception {
    mockMvc.perform(delete("/products/" + product.getProductId()))
        .andExpect(status().isOk());
  }
}