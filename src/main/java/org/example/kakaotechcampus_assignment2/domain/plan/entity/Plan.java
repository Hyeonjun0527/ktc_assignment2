package org.example.kakaotechcampus_assignment2.domain.plan.entity;

import java.time.LocalDateTime;

public record Plan(Long id,
                   Long memberId,
                   String content,
                   String pwd,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
