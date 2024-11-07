package com.chibuisi.api.controller;

import com.chibuisi.api.model.TaskDto;
import com.chibuisi.api.transformer.TaskTransformer;
import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.model.TaskStatus;
import com.chibuisi.domain.service.TaskService;
import io.micronaut.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskTransformer taskTransformer;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTask_shouldReturnCreatedResponseWithTaskDto() {
        // Arrange
        Instant createdTime = Instant.parse("2024-11-06T23:22:30.913109Z");
        Instant updatedTime = Instant.parse("2024-11-06T23:22:36.176713Z");

        TaskDto taskDto = TaskDto.builder()
                .title("Sample Task")
                .description("Sample Description")
                .status(TaskStatus.PENDING)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();

        Task task = Task.builder()
                .id(1L)
                .title("Sample Task")
                .description("Sample Description")
                .status(TaskStatus.PENDING)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();

        // Mocking the transformations and service call
        when(taskTransformer.fromDto(taskDto)).thenReturn(task);
        when(taskService.saveTask(any(Task.class))).thenReturn(task);
        when(taskTransformer.fromTask(task)).thenReturn(taskDto);

        // Act
        HttpResponse<TaskDto> response = taskController.saveTask(taskDto);

        // Assert
        assertEquals(201, response.getStatus().getCode());
        assertEquals(taskDto, response.body());
    }
}
