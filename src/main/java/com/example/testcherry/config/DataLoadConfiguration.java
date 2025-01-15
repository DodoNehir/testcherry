package com.example.testcherry.config;

import com.example.testcherry.domain.product.dto.ProductDto;
import com.example.testcherry.domain.member.dto.JoinRequestBody;
import com.example.testcherry.domain.member.service.MemberService;
import com.example.testcherry.domain.product.service.ProductService;
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

//    memberService.join(new JoinRequestBody(
//        "qwe", "qwe",
//        "Seoul",
//        "010-0000-0000"
//    ));

//    for (int i = 0; i < 20; i++) {
//      JoinRequestBody joinRequestBody = new JoinRequestBody(
//          faker.twitter().twitterId(10),
//          faker.internet().password(),
//          faker.address().fullAddress(),
//          faker.phoneNumber().phoneNumber()
//      );
//      memberService.join(joinRequestBody);
//    }

    for (int i = 0; i < 5; i++) {
      ProductDto productDto = new ProductDto(
          faker.food().dish(),
          faker.food().ingredient(),
          faker.number().numberBetween(10, 10000),
          faker.number().numberBetween(80, 400) * 100,
          "/images/product_" + (i + 1) + ".jpg"
      );
      productService.registerProduct(productDto);
    }

  }

}
