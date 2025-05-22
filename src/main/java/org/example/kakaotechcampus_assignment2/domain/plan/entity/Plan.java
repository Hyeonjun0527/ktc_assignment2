package org.example.kakaotechcampus_assignment2.domain.plan.entity;

import org.example.kakaotechcampus_assignment2.domain.member.entity.Member;

import java.time.LocalDateTime;


public record Plan(Long id,
                   Member member,
                   String content,
                   String pwd,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
