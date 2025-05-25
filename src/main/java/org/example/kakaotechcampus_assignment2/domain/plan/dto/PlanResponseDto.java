package org.example.kakaotechcampus_assignment2.domain.plan.dto;

import org.example.kakaotechcampus_assignment2.domain.plan.entity.AuthorInfo;
import java.time.LocalDateTime;

public record PlanResponseDto(
    Long id,
    AuthorInfo authorInfo,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
} 