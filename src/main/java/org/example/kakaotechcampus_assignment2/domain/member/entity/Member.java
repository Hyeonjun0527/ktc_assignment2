package org.example.kakaotechcampus_assignment2.domain.member.entity;

import java.time.LocalDateTime;

public record Member(
    Long id,
    String name,
    String email,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}