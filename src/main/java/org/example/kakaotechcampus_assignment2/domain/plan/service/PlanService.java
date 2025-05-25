package org.example.kakaotechcampus_assignment2.domain.plan.service;

import org.example.kakaotechcampus_assignment2.domain.plan.dto.PageResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanCreateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanUpdateRequestDto;

import java.time.LocalDate;

public interface PlanService {

    void createPlan(PlanCreateRequestDto request);

    PlanResponseDto getPlanById(Long id);

    PageResponseDto<PlanResponseDto> getAllPlans(LocalDate modifiedAt, Long memberId, int page, int size);

    PlanResponseDto updatePlan(Long planId, PlanUpdateRequestDto request);

    void deletePlan(Long planId, String pwd, Long memberId);

}