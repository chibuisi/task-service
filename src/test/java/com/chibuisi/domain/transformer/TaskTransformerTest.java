package com.chibuisi.domain.transformer;

import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.model.TaskStatus;
import com.chibuisi.infrastructure.postgres.enitity.Status;
import com.chibuisi.infrastructure.postgres.enitity.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTransformerTest {

    private TaskTransformer taskTransformer;

    @BeforeEach
    void setUp() {
        taskTransformer = new TaskTransformer();
    }

    @Test
    void fromTask_shouldTransformTaskToTaskEntity() {
        // Arrange
        Task task = Task.builder()
                .title("Sample Task")
                .description("Sample Description")
                .createdTime(Instant.parse("2024-11-06T23:22:30.913109Z"))
                .status(TaskStatus.PENDING)
                .build();

        // Act
        TaskEntity taskEntity = taskTransformer.fromTask(task);

        // Assert
        assertEquals(task.getTitle(), taskEntity.getTitle());
        assertEquals(task.getDescription(), taskEntity.getDescription());
        assertEquals(task.getCreatedTime(), taskEntity.getCreatedAt());
        assertEquals(Status.PENDING, taskEntity.getStatus());
    }

    @Test
    void fromTaskEntity_shouldTransformTaskEntityToTask() {
        // Arrange
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle("Sample Task");
        taskEntity.setDescription("Sample Description");
        taskEntity.setCreatedAt(Instant.parse("2024-11-06T23:22:30.913109Z"));
        taskEntity.setUpdatedAt(Instant.parse("2024-11-06T23:22:36.176713Z"));
        taskEntity.setStatus(Status.PENDING);

        // Act
        Task task = taskTransformer.fromTaskEntity(taskEntity);

        // Assert
        assertEquals(taskEntity.getId(), task.getId());
        assertEquals(taskEntity.getTitle(), task.getTitle());
        assertEquals(taskEntity.getDescription(), task.getDescription());
        assertEquals(taskEntity.getCreatedAt(), task.getCreatedTime());
        assertEquals(taskEntity.getUpdatedAt(), task.getUpdatedTime());
        assertEquals(TaskStatus.PENDING, task.getStatus());
    }
}
