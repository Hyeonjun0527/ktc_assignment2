package org.example.kakaotechcampus_assignment2.domain.plan.repository;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    
    public void save(Plan plan) {
        LocalDateTime now = LocalDateTime.now();

        String sql = "INSERT INTO " +
                 "Plan (content, writer_name, pwd, created_at, updated_at) " +
                 "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, plan.content(), plan.writerName(), plan.pwd(), Timestamp.valueOf(now), Timestamp.valueOf(now));
    }

    @Override
    public PlanResponseDto findById(Long id) {
        String sql = "SELECT * " +
                     "FROM Plan p " +
                     "WHERE p.id = ?";
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> new PlanResponseDto(
                    rs.getLong("id"),
                    rs.getString("content"),
                    rs.getString("writer_name"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            ), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Plan findPlanEntityById(Long id) {
        String sql = "SELECT *" +
                "FROM Plan WHERE id = ?";
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> new Plan(
                    rs.getLong("id"),
                    rs.getString("writer_name"),
                    rs.getString("content"),
                    rs.getString("pwd"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            ), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<PlanResponseDto> findAll() {
        String sql = "SELECT *" +
                     "FROM Plan p " +
                     "ORDER BY p.updated_at DESC";
        return jdbc.query(sql, (rs, rowNum) -> new PlanResponseDto(
                rs.getLong("id"),
                rs.getString("content"),
                rs.getString("writer_name"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        ));
    }

    @Override
    public int update(Long id, Plan plan) {
        String sql = "UPDATE Plan " +
                "SET content = ?, writer_name = ?, updated_at = ?" +
                "WHERE id = ?";
        return jdbc.update(sql, plan.content(), plan.writerName(), Timestamp.valueOf(LocalDateTime.now()), id);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM " +
                "Plan " +
                "WHERE id = ?";
        return jdbc.update(sql, id);
    }

    public List<PlanResponseDto> findAll(LocalDate updatedAt, String writerName) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Plan");
        List<Object> params = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        if (updatedAt != null) {
            conditions.add("DATE(updated_at) = ?");
            params.add(updatedAt);
        }
        if (writerName != null) {
            conditions.add("writer_name = ?");
            params.add(writerName);
        }
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        sql.append(" ORDER BY updated_at DESC");

        return jdbc.query(
            sql.toString(),
            (rs, rowNum) -> new PlanResponseDto(
                rs.getLong("id"),
                rs.getString("content"),
                rs.getString("writer_name"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
            ),
            params.toArray()
        );
    }
}
