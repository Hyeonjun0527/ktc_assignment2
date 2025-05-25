package org.example.kakaotechcampus_assignment2.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.member.entity.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("modified_at").toLocalDateTime()
    );

    @Override
    public Member findByEmail(String email) {
        String sql = """
                SELECT id, name, email, created_at, modified_at
                FROM member
                WHERE email = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, memberRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Member save(Member member) {
        LocalDateTime now = LocalDateTime.now();
        String sql = """
                INSERT INTO member (name, email, created_at, modified_at)
                VALUES (?, ?, ?, ?)
                """;
        
        jdbcTemplate.update(sql, member.name(), member.email(), Timestamp.valueOf(now), Timestamp.valueOf(now));

        return new Member(null, member.name(), member.email(), now, now);
    }
} 