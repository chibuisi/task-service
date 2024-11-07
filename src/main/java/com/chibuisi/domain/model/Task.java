package com.chibuisi.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private Instant createdTime;
    private Instant updatedTime;
    private TaskStatus status;
}
