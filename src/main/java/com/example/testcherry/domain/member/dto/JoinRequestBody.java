package com.example.testcherry.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JoinRequestBody(

    @NotBlank(message = "아이디를 입력해주세요")
    String username,

    @NotBlank(message = "비밀번호를 입력해주세요")
    String password,

    @NotBlank(message = "주소를 입력해주세요")
    String address,

    @Length(max = 15)
    @NotBlank(message = "전화번호를 입력해주세요")
    String phoneNumber) {

}
