package org.example.kakaotechcampus_assignment2.domain.plan.repository;

import org.example.kakaotechcampus_assignment2.domain.plan.entity.Plan;

public interface PlanRepository {

    void createPlan(Plan plan);

    Plan readPlan(Plan plan);
}
