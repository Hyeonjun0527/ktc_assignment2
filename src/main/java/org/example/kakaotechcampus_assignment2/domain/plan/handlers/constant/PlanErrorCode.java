package org.example.kakaotechcampus_assignment2.domain.plan.handlers.constant;

import lombok.Getter;

@Getter
public enum PlanErrorCode {
    
    PLAN_NOT_FOUND("PLAN_001", "요청한 일정을 찾을 수 없습니다."),
    INVALID_PLAN_PASSWORD("PLAN_002", "일정의 비밀번호가 올바르지 않습니다."),
    VALIDATION_ERROR("PLAN_003", "입력값이 유효하지 않습니다."),
    SYSTEM_ERROR("SYSTEM_001", "서버에서 예상치 못한 오류가 발생했습니다.");
    
    private final String code;
    private final String message;
    
    PlanErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

} 