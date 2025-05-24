package org.example.kakaotechcampus_assignment2.domain.plan.dto;

public record PlanUpdateRequestDto(
    String content,
    String writerName,
    String password
) {
} 