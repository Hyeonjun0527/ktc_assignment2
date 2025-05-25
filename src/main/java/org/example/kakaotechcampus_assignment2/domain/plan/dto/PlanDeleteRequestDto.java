package org.example.kakaotechcampus_assignment2.domain.plan.dto;

public record PlanDeleteRequestDto(
        String content,
        String pwd,
        Long memberId
) {
}