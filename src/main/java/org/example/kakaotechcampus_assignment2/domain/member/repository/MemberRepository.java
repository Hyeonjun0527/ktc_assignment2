package org.example.kakaotechcampus_assignment2.domain.member.repository;

import org.example.kakaotechcampus_assignment2.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member findByEmail(String email);
    Member save(Member member);
}
