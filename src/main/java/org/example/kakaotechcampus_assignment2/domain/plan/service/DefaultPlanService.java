package org.example.kakaotechcampus_assignment2.domain.plan.service;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.member.entity.Member;
import org.example.kakaotechcampus_assignment2.domain.member.repository.MemberRepository;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanCreateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanUpdateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;
import org.example.kakaotechcampus_assignment2.domain.plan.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultPlanService implements PlanService {

    private final PlanRepository planRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createPlan(PlanCreateRequestDto request) {
        Member member = memberRepository.findByEmail(request.memberEmail());

        Plan planToSave = new Plan(
                null,
                member.id(),
                request.content(),
                request.pwd(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        planRepository.save(planToSave);
    }

    @Override
    public PlanResponseDto getPlanById(Long id) {
        return planRepository.findById(id);
    }

    @Override
    public List<PlanResponseDto> getAllPlans(LocalDate modifiedAt, Long memberId) {
        return planRepository.findAllByCondition(modifiedAt, memberId);
    }

    @Transactional
    @Override
    public PlanResponseDto updatePlan(Long planId, PlanUpdateRequestDto request, Long memberId) {
        Plan existingPlan = planRepository.findPlanEntityById(planId);
        
        Plan planToUpdate = new Plan(
                existingPlan.id(),
                existingPlan.memberId(),
                request.content(),
                existingPlan.pwd(),
                existingPlan.createdAt(),
                LocalDateTime.now()
        );

        planRepository.update(planId, planToUpdate);

        return planRepository.findById(planId);
    }

    @Transactional
    @Override
    public void deletePlan(Long id, String pwd, Long memberId) {
        planRepository.deleteById(id);
    }
} 