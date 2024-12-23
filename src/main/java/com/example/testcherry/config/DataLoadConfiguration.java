package com.example.testcherry.config;

import com.example.testcherry.model.dto.MemberDto;
import com.example.testcherry.model.dto.ProductDto;
import com.example.testcherry.service.MemberService;
import com.example.testcherry.service.ProductService;
import net.datafaker.Faker;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class DataLoadConfiguration implements ApplicationRunner {

  private final MemberService memberService;
  private final ProductService productService;

  public DataLoadConfiguration(MemberService memberService, ProductService productService) {
    this.memberService = memberService;
    this.productService = productService;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Faker faker = new Faker();

    memberService.join(new MemberDto(
        "qwe", "qwe",
        "Seoul",
        "010-0000-0000"
    ));

    for (int i = 0; i < 20; i++) {
      MemberDto memberDto = new MemberDto(
          faker.twitter().twitterId(10),
          faker.internet().password(),
          faker.address().fullAddress(),
          faker.phoneNumber().phoneNumber()
      );
      memberService.join(memberDto);
    }

    for (int i = 0; i < 50; i++) {
      ProductDto productDto = new ProductDto(
          faker.food().dish(),
          faker.food().ingredient(),
          faker.number().numberBetween(10, 10000)
      );
      productService.registerProduct(productDto);
    }

  }

}
