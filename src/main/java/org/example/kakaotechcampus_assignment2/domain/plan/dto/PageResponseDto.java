package org.example.kakaotechcampus_assignment2.domain.plan.dto;

import java.util.List;

public record PageResponseDto<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isLast) {
    public static <T> PageResponseDto<T> of(List<T> content, int pageNumber, int pageSize, long totalElements) {
        if (pageNumber < 1) {

        }
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        if (totalPages == 0 && totalElements > 0) {
            totalPages = 1;
        }
        if (totalPages == 0 && totalElements == 0) {
            totalPages = 0;
        }
        boolean isLast = (pageNumber >= totalPages);
        if (totalElements == 0) {
            isLast = true;
        }

        return new PageResponseDto<>(content, pageNumber, pageSize, totalElements, totalPages, isLast);
    }
}