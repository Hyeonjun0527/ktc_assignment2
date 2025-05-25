package org.example.kakaotechcampus_assignment2.domain.plan.dto;

import java.time.LocalDateTime;

public record PlanResponseDto(
    Long id,
    String content,
    String writerName,
    Long memberId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
} 