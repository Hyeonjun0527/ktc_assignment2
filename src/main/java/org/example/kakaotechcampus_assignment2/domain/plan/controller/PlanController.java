package org.example.kakaotechcampus_assignment2.domain.plan.controller;

import lombok.RequiredArgsConstructor;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanCreateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.dto.PlanUpdateRequestDto;
import org.example.kakaotechcampus_assignment2.domain.plan.service.PlanService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<Void> createPlan(@RequestBody PlanCreateRequestDto request) {
        planService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDto>> getAllPlans(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedAt,
            @RequestParam(required = false) String writerName) {
        return ResponseEntity.ok(planService.getAllPlans(updatedAt, writerName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDto> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDto> updatePlan(@PathVariable Long id, @RequestBody PlanUpdateRequestDto request) {
        return ResponseEntity.ok(planService.updatePlan(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id, @RequestParam String password) {
        planService.deletePlan(id, password);
        return ResponseEntity.noContent().build();
    }
} 