package org.example.kakaotechcampus_assignment2.domain.plan.service;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.member.entity.Member;
import org.example.kakaotechcampus_assignment2.domain.member.repository.MemberRepository;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PageResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanCreateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanUpdateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.AuthorInfo;
import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;
import org.example.kakaotechcampus_assignment2.domain.plan.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultPlanService implements PlanService {

    private final PlanRepository planRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createPlan(PlanCreateRequestDto request) {
        Member member = memberRepository.findByEmail(request.memberEmail());
        AuthorInfo authorInfo;
        if (member == null) {
            authorInfo = new AuthorInfo(null, null);
        } else {
            authorInfo = new AuthorInfo(member.name(), member.email());
        }
        Plan planToSave = new Plan(
                null,
                authorInfo,
                request.content(),
                request.pwd(),
                LocalDateTime.now(),
                LocalDateTime.now());
        planRepository.save(planToSave);
    }

    @Override
    public PlanResponseDto getPlanById(Long id) {
        return planRepository.findById(id);
    }

    @Transactional
    @Override
    public PlanResponseDto updatePlan(Long planId, PlanUpdateRequestDto request) {
        Plan existingPlan = planRepository.findPlanEntityById(planId);

        if (existingPlan == null) {
            return null;
        }

        if (!Objects.equals(existingPlan.authorInfo().name(), request.memberId())) {
            return null;
        }

        if (!Objects.equals(existingPlan.pwd(), request.pwd())) {
            return null;
        }

        Plan planToUpdate = new Plan(
                existingPlan.id(),
                existingPlan.authorInfo(),
                request.content(),
                existingPlan.pwd(),
                existingPlan.createdAt(),
                LocalDateTime.now());

        planRepository.update(planId, planToUpdate);

        return planRepository.findById(planId);
    }

    @Transactional
    @Override
    public void deletePlan(Long planId, String pwd, Long memberId) {
        planRepository.deleteById(planId);
    }

    @Override
    public PageResponseDto<PlanResponseDto> getAllPlans(LocalDate modifiedAt, Long memberId, int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        long totalElements = planRepository.countFilteredPlans(modifiedAt, memberId);

        if (totalElements == 0) {
            return PageResponseDto.of(Collections.emptyList(), page, size, 0L);
        }

        int offset = (page - 1) * size;
        if (offset >= totalElements) {
             return PageResponseDto.of(Collections.emptyList(), page, size, totalElements);
        }
        
        List<PlanResponseDto> content = planRepository.findAll(modifiedAt, memberId, offset, size);
        return PageResponseDto.of(content, page, size, totalElements);
    }
}