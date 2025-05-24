package org.example.kakaotechcampus_assignment2.domain.plan.entity;

import java.time.LocalDateTime;

public record Plan(Long id,
                   String writerName,
                   String content,
                   String pwd,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
