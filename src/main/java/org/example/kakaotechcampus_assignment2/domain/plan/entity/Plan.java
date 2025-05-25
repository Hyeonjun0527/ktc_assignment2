package org.example.kakaotechcampus_assignment2.domain.plan.entity;

import java.time.LocalDateTime;

public record Plan(
    Long id,
    AuthorInfo authorInfo,
    String content,
    String pwd,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
