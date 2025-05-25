package org.example.kakaotechcampus_assignment2.domain.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlanUpdateRequestDto(
    @NotBlank(message = "비밀번호를 입력해주세요")
    String pwd,
    @NotBlank(message = "내용을 입력해주세요")
    @Size(max = 200, message = "200자 내로 작성해주세요")
    String content
) {
} 