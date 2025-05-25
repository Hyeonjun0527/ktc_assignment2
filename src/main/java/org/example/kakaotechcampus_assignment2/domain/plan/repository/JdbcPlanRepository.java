package org.example.kakaotechcampus_assignment2.domain.plan.repository;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
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

    private final RowMapper<PlanResponseDto> planResponseDtoRowMapper = (rs, rowNum) -> new PlanResponseDto(
            rs.getLong("plan_id"),
            rs.getString("plan_content"),
            rs.getString("member_name"),
            rs.getLong("member_id"),
            rs.getTimestamp("plan_created_at").toLocalDateTime(),
            rs.getTimestamp("plan_updated_at").toLocalDateTime()
    );

    private final RowMapper<Plan> planEntityRowMapper = (rs, rowNum) -> new Plan(
            rs.getLong("id"),
            rs.getLong("member_id"),
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
        jdbc.update(sql, plan.memberId(), plan.content(), plan.pwd(), Timestamp.valueOf(now), Timestamp.valueOf(now));
    }

    @Override
    public PlanResponseDto findById(Long id) {
        String sql = """
                SELECT
                    p.id AS plan_id,
                    p.content AS plan_content,
                    p.member_id,
                    p.created_at AS plan_created_at,
                    p.updated_at AS plan_updated_at,
                    m.name AS member_name
                FROM
                    Plan p
                JOIN
                    Member m ON p.member_id = m.id
                WHERE
                    p.id = ?
                """;
        try {
            return jdbc.queryForObject(sql, planResponseDtoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Plan findPlanEntityById(Long id) {
        String sql = """
                SELECT
                    id, member_id, content, pwd, created_at, updated_at
                FROM
                    Plan
                WHERE
                    id = ?
                """;
        try {
            return jdbc.queryForObject(sql, planEntityRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<PlanResponseDto> findAll() {
        String sql = """
                SELECT
                    p.id AS plan_id,
                    p.content AS plan_content,
                    p.member_id,
                    p.created_at AS plan_created_at,
                    p.updated_at AS plan_updated_at,
                    m.name AS member_name
                FROM
                    Plan p
                JOIN
                    Member m ON p.member_id = m.id
                ORDER BY
                    p.updated_at DESC
                """;
        return jdbc.query(sql, planResponseDtoRowMapper);
    }

    @Override
    public int update(Long id, Plan plan) {
        String sql = """
                UPDATE Plan
                SET
                    content = ?,
                    updated_at = ?
                WHERE
                    id = ? AND member_id = ?
                """;
        return jdbc.update(sql, plan.content(), Timestamp.valueOf(LocalDateTime.now()), id, plan.memberId());
    }

    @Override
    public int deleteById(Long id) {
        String sql = """
                DELETE FROM Plan
                WHERE id = ?
                """;
        return jdbc.update(sql, id);
    }

    @Override
    public List<PlanResponseDto> findAllByCondition(LocalDate modifiedAt, Long memberId) {
        StringBuilder sql = new StringBuilder(
            """
            SELECT 
                p.id AS plan_id, 
                p.content AS plan_content,
                p.member_id,
                p.created_at AS plan_created_at,
                p.updated_at AS plan_updated_at, 
                m.name AS member_name
            FROM Plan p JOIN Member m ON p.member_id = m.id
            """
        );
        List<Object> params = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        if (modifiedAt != null) {
            conditions.add("DATE(p.updated_at) = ?");
            params.add(modifiedAt);
        }
        if (memberId != null) {
            conditions.add("p.member_id = ?");
            params.add(memberId);
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        sql.append(" ORDER BY p.updated_at DESC");

        return jdbc.query(sql.toString(), planResponseDtoRowMapper, params.toArray());
    }
}
