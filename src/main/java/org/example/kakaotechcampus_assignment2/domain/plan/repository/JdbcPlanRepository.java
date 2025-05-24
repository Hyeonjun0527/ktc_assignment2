package org.example.kakaotechcampus_assignment2.domain.plan.repository;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcPlanRepository implements PlanRepository {

    private final JdbcTemplate jdbc;

    
    public void save(Plan plan) {
        LocalDateTime now = LocalDateTime.now();

        String sql = "INSERT INTO " +
                 "Plan (content, writer_name, pwd, created_at, modified_at) " +
                 "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, plan.content(), plan.writerName(), plan.pwd(), Timestamp.valueOf(now), Timestamp.valueOf(now));
    }

    @Override
    public PlanResponseDto findById(Long id) {
        String sql = "SELECT *" +
                     "FROM Plan p " +
                     "WHERE p.id = ?";
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> new PlanResponseDto(
                    rs.getLong("id"),
                    rs.getString("content"),
                    rs.getString("writer"),
                    rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("modified_at").toLocalDateTime()
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
                    rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("modified_at").toLocalDateTime()
            ), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<PlanResponseDto> findAll() {
        String sql = "SELECT *" +
                     "FROM Plan p " +
                     "ORDER BY p.modified_at DESC";
        return jdbc.query(sql, (rs, rowNum) -> new PlanResponseDto(
                rs.getLong("id"),
                rs.getString("content"),
                rs.getString("writer"),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        ));
    }

    @Override
    public int update(Long id, Plan plan) {
        String sql = "UPDATE Plan " +
                "SET content = ?, modified_at = ?" +
                "WHERE id = ?";
        return jdbc.update(sql, plan.content(), Timestamp.valueOf(LocalDateTime.now()), id);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM" +
                "Plan" +
                "WHERE id = ?";
        return jdbc.update(sql, id);
    }
}
