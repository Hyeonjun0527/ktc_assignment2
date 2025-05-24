package org.example.kakaotechcampus_assignment2.domain.plan.repository;

import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;

import java.util.List;

public interface PlanRepository {

    void save(Plan plan);

    List<PlanResponseDto> findAll();

    PlanResponseDto findById(Long id);

    Plan findPlanEntityById(Long id);

    int update(Long id, Plan plan);

    int deleteById(Long id);

}
