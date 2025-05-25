package org.example.kakaotechcampus_assignment2.domain.plan.controller;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PageResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanCreateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanUpdateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.service.PlanService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public ResponseEntity<PageResponseDto<PlanResponseDto>> getAllPlansPaginated(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate modifiedAt,
            @RequestParam(required = false) Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponseDto<PlanResponseDto> paginatedPlans = planService.getAllPlans(modifiedAt, memberId, page, size);
        return ResponseEntity.ok(paginatedPlans);
    }

    @PostMapping
    public ResponseEntity<String> createPlan(@RequestBody @Valid PlanCreateRequestDto request) {
        planService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("생성되었습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDto> getPlanById(@PathVariable Long id) {
        PlanResponseDto plan = planService.getPlanById(id);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlan(@PathVariable Long id, @RequestBody @Valid PlanUpdateRequestDto request) {
        PlanResponseDto updatedPlan = planService.updatePlan(id, request);
        if (updatedPlan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id, @RequestParam String pwd) {
        Long currentMemberId = 1L;
        planService.deletePlan(id, pwd, currentMemberId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
} 