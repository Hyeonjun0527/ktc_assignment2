package org.example.kakaotechcampus_assignment2.domain.plan.repository;

import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;

import java.util.List;
import java.time.LocalDate;

public interface PlanRepository {

    void save(Plan plan);

    List<PlanResponseDto> findAll();

    List<PlanResponseDto> findAll(LocalDate modifiedAt, Long memberId, int offset, int limit);

    long countFilteredPlans(LocalDate modifiedAt, Long memberId);

    PlanResponseDto findById(Long id);

    Plan findPlanEntityById(Long id);

    int update(Long id, Plan plan);

    int deleteById(Long id);

}
