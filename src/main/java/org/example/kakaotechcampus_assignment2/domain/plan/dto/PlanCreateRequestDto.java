package org.example.kakaotechcampus_assignment2.domain.plan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlanCreateRequestDto(
    @NotBlank(message = "내용을 입력해주세요")
    @Size(max = 200, message = "200자 내로 작성해주세요")
    String content,

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String memberEmail,

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 20, message = "20자 내로 작성해주세요")
    String memberName,

    @NotBlank(message = "비밀번호를 입력해주세요")
    String pwd
) {
} 