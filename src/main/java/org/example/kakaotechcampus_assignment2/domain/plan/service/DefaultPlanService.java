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
import org.example.kakaotechcampus_assignment2.domain.plan.handlers.InvalidPlanPasswordException;
import org.example.kakaotechcampus_assignment2.domain.plan.handlers.PlanNotFoundException;
import org.example.kakaotechcampus_assignment2.domain.plan.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
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
        if (member == null) {
            member = new Member(null, request.memberName(), request.memberEmail(), LocalDateTime.now(), LocalDateTime.now());
            memberRepository.save(member);
        }
        AuthorInfo authorInfo = new AuthorInfo(member.name(), member.email());
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
        PlanResponseDto plan = planRepository.findById(id);
        if (plan == null) {
            throw new PlanNotFoundException("일정 정보를 찾을 수 없습니다.");
        }
        return plan;
    }

    @Transactional
    @Override
    public PlanResponseDto updatePlan(Long planId, PlanUpdateRequestDto request) {
        Plan existingPlan = planRepository.findPlanEntityById(planId);
        if (existingPlan == null) {
            throw new PlanNotFoundException("수정할 일정 정보를 찾을 수 없습니다.");
        }
        if (!existingPlan.pwd().equals(request.pwd())) {
            throw new InvalidPlanPasswordException("비밀번호가 일치하지 않습니다.");
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
        Plan existingPlan = planRepository.findPlanEntityById(planId);
        if (existingPlan == null) {
            throw new PlanNotFoundException("삭제할 일정 정보를 찾을 수 없습니다.");
        }
        if (!existingPlan.pwd().equals(pwd)) {
            throw new InvalidPlanPasswordException("비밀번호가 일치하지 않습니다.");
        }
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