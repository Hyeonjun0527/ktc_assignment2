package org.example.kakaotechcampus_assignment2.domain.plan.dto;

public record PlanCreateRequestDto(
    String content,
    String memberEmail,
    String pwd
) {
} 