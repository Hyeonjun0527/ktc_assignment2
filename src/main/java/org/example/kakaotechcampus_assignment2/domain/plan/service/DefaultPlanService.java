package org.example.kakaotechcampus_assignment2.domain.plan.service;

import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanCreateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanUpdateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;
import org.example.kakaotechcampus_assignment2.domain.plan.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultPlanService implements PlanService {

    private final PlanRepository planRepository;

    @Transactional
    public void createPlan(PlanCreateRequestDto request) {
        Plan planToSave = new Plan(
                null,
                request.writerName(),
                request.content(),
                request.pwd(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        planRepository.save(planToSave);
    }

    public PlanResponseDto getPlanById(Long id) {
        PlanResponseDto planResponseDto = planRepository.findById(id);
        return planResponseDto;
    }

    public List<PlanResponseDto> getAllPlans(LocalDate updatedAt, String writerName) {
        return planRepository.findAll(updatedAt, writerName);
    }

    @Transactional
    public PlanResponseDto updatePlan(Long id, PlanUpdateRequestDto request) {
        Plan existingPlan = planRepository.findPlanEntityById(id);

        //POLICY : 이름과 컨텐트가 null인 경우 기존 값을 유지
        String newWriterName = request.writerName() != null ? request.writerName() : existingPlan.writerName();
        String newContent = request.content() != null ? request.content() : existingPlan.content();

        Plan planToUpdate = new Plan(
                existingPlan.id(),
                newWriterName,
                newContent,
                existingPlan.pwd(),
                existingPlan.createdAt(),
                LocalDateTime.now()
        );

        int updatedRows = planRepository.update(id, planToUpdate);
        log.info("updatedRows: {}", updatedRows);

        PlanResponseDto updatedPlanResponseDto = planRepository.findById(id);
        return updatedPlanResponseDto;
    }

    @Transactional
    public void deletePlan(Long id, String password) {
        planRepository.deleteById(id);
    }

} 