package org.example.kakaotechcampus_assignment2.domain.plan.service;

import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanCreateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanUpdateRequestDto;

import java.time.LocalDate;
import java.util.List;

public interface PlanService {

    void createPlan(PlanCreateRequestDto request);

    PlanResponseDto getPlanById(Long id);

    List<PlanResponseDto> getAllPlans(LocalDate modifiedAt, Long memberId);

    PlanResponseDto updatePlan(Long planId, PlanUpdateRequestDto request, Long memberId);

    void deletePlan(Long planId, String pwd, Long memberId);
} 