package com.chibuisi.api.transformer;

import com.chibuisi.api.model.TaskDto;
import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTransformerTest {

    private TaskTransformer taskTransformer;

    @BeforeEach
    void setUp() {
        taskTransformer = new TaskTransformer();
    }

    @Test
    void fromDto_shouldTransformTaskDtoToTask() {
        // Arrange
        Instant createdTime = Instant.parse("2024-11-06T23:22:30.913109Z");
        Instant updatedTime = Instant.parse("2024-11-06T23:22:36.176713Z");
        TaskDto taskDto = TaskDto.builder()
                .title("Sample Task")
                .description("Sample Description")
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .status(TaskStatus.PENDING)
                .build();

        // Act
        Task task = taskTransformer.fromDto(taskDto);

        // Assert
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getDescription(), task.getDescription());
        assertEquals(taskDto.getCreatedTime(), task.getCreatedTime());
        assertEquals(taskDto.getUpdatedTime(), task.getUpdatedTime());
        assertEquals(taskDto.getStatus(), task.getStatus());
    }

    @Test
    void fromDto_shouldUseCurrentTimeIfCreatedOrUpdatedTimeIsNull() {
        // Arrange
        TaskDto taskDto = TaskDto.builder()
                .title("Sample Task")
                .description("Sample Description")
                .status(TaskStatus.PENDING)
                .build();

        // Act
        Task task = taskTransformer.fromDto(taskDto);

        // Assert
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getDescription(), task.getDescription());
        assertEquals(taskDto.getStatus(), task.getStatus());
        assertNotNull(task.getCreatedTime());
        assertNotNull(task.getUpdatedTime());
    }

    @Test
    void fromTask_shouldTransformTaskToTaskDto() {
        // Arrange
        Instant createdTime = Instant.parse("2024-11-06T23:22:30.913109Z");
        Instant updatedTime = Instant.parse("2024-11-06T23:22:36.176713Z");
        Task task = Task.builder()
                .id(1L)
                .title("Sample Task")
                .description("Sample Description")
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .status(TaskStatus.PENDING)
                .build();

        // Act
        TaskDto taskDto = taskTransformer.fromTask(task);

        // Assert
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getCreatedTime(), taskDto.getCreatedTime());
        assertEquals(task.getUpdatedTime(), taskDto.getUpdatedTime());
        assertEquals(task.getStatus(), taskDto.getStatus());
    }
}
