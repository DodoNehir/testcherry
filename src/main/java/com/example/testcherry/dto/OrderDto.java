package com.example.testcherry.dto;

import java.util.Map;

public record OrderDto(
    Long memberId,
    Map<Long, Integer> quantityOfProducts // < product id , 개수 >
) {

}
