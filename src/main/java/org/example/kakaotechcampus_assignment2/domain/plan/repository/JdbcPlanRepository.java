package org.example.kakaotechcampus_assignment2.domain.plan.repository;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.AuthorInfo;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class JdbcPlanRepository implements PlanRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Plan> planEntityRowMapper = (rs, rowNum) -> new Plan(
            rs.getLong("id"),
            new AuthorInfo(
                rs.getString("member_name"),
                rs.getString("member_email")
            ),
            rs.getString("content"),
            rs.getString("pwd"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
    );

    @Override
    public void save(Plan plan) {
        LocalDateTime now = LocalDateTime.now();
        String sql = """
                INSERT INTO Plan (member_id, content, pwd, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?)
                """;
        Long memberId = null;
        if (plan.authorInfo() != null && plan.authorInfo().email() != null) {
            String memberSql = "SELECT id FROM member WHERE email = ?";
            try {
                memberId = jdbc.queryForObject(memberSql, Long.class, plan.authorInfo().email());
            } catch (EmptyResultDataAccessException e) {
                memberId = null;
            }
        }
        jdbc.update(sql, memberId, plan.content(), plan.pwd(), Timestamp.valueOf(now), Timestamp.valueOf(now));
    }

    @Override
    public PlanResponseDto findById(Long id) {
        String sql = """
                SELECT
                    p.id AS plan_id,
                    p.content AS plan_content,
                    p.created_at AS plan_created_at,
                    p.updated_at AS plan_updated_at,
                    m.name AS member_name,
                    m.email AS member_email
                FROM
                    Plan p
                LEFT JOIN
                    Member m ON p.member_id = m.id
                WHERE
                    p.id = ?
                """;
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> new PlanResponseDto(
                rs.getLong("plan_id"),
                new AuthorInfo(
                    rs.getString("member_name"),
                    rs.getString("member_email")
                ),
                rs.getString("plan_content"),
                rs.getTimestamp("plan_created_at").toLocalDateTime(),
                rs.getTimestamp("plan_updated_at").toLocalDateTime()
            ), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Plan findPlanEntityById(Long id) {
        String sql = """
                SELECT
                    p.id, p.content, p.pwd, p.created_at, p.updated_at,
                    m.name AS member_name, m.email AS member_email
                FROM
                    Plan p
                LEFT JOIN
                    Member m ON p.member_id = m.id
                WHERE
                    p.id = ?
                """;
        try {
            return jdbc.queryForObject(sql, planEntityRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int update(Long id, Plan plan) {
        String sql = """
                UPDATE Plan
                SET
                    content = ?,
                    updated_at = ?
                WHERE
                    id = ?
                """;
        return jdbc.update(sql, plan.content(), Timestamp.valueOf(LocalDateTime.now()), id);
    }

    @Override
    public int deleteById(Long id) {
        String sql = """
                DELETE FROM Plan
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    private StringBuilder makeQuery() {
        return new StringBuilder(
            """
            SELECT 
                p.id AS plan_id, 
                p.content AS plan_content,
                p.created_at AS plan_created_at,
                p.updated_at AS plan_updated_at, 
                m.name AS member_name,
                m.email AS member_email
            FROM Plan p LEFT JOIN Member m ON p.member_id = m.id
            """
        );
    }

    private List<String> makeConditions(LocalDate updatedAt, Long memberId, List<Object> params) {
        List<String> conditions = new ArrayList<>();
        if (updatedAt != null) {
            conditions.add("DATE(p.updated_at) = ?");
            params.add(updatedAt);
        }
        if (memberId != null) {
            conditions.add("p.member_id = ?");
            params.add(memberId);
        }
        return conditions;
    }

    @Override
    public List<PlanResponseDto> findAll(LocalDate modifiedAt, Long memberId, int offset, int limit) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = makeQuery();
        List<String> conditions = makeConditions(modifiedAt, memberId, params);

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        sql.append(" ORDER BY p.updated_at DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        return jdbc.query(sql.toString(), (rs, rowNum) -> new PlanResponseDto(
            rs.getLong("plan_id"),
            new AuthorInfo(
                rs.getString("member_name"),
                rs.getString("member_email")
            ),
            rs.getString("plan_content"),
            rs.getTimestamp("plan_created_at").toLocalDateTime(),
            rs.getTimestamp("plan_updated_at").toLocalDateTime()
        ), params.toArray());
    }

    @Override
    public long countFilteredPlans(LocalDate updatedAt, Long memberId) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                """
                SELECT COUNT(*)
                FROM Plan p
                LEFT JOIN Member m
                ON p.member_id = m.id
                """);
        List<String> conditions = makeConditions(updatedAt, memberId, params);

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        Long count = jdbc.queryForObject(sql.toString(), Long.class, params.toArray());
        return count != null ? count : 0L;
    }
}
