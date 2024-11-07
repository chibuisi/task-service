package com.chibuisi.infrastructure.service;

import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.model.TaskStatus;
import com.chibuisi.domain.transformer.TaskTransformer;
import com.chibuisi.infrastructure.postgres.enitity.TaskEntity;
import com.chibuisi.infrastructure.postgres.repository.TaskRepository;
import com.chibuisi.infrastructure.service.TaskEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TaskEntityServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskTransformer taskTransformer;

    @InjectMocks
    private TaskEntityService taskEntityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTask_shouldSaveAndReturnTransformedTask() {
        // Arrange
        Task task = Task.builder()
                .title("Sample Task")
                .description("Sample Description")
                .createdTime(Instant.parse("2024-11-06T23:22:30.913109Z"))
                .status(TaskStatus.PENDING)
                .build();

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle("Sample Task");
        taskEntity.setDescription("Sample Description");
        taskEntity.setCreatedAt(Instant.parse("2024-11-06T23:22:30.913109Z"));
        taskEntity.setUpdatedAt(Instant.now());
        taskEntity.setStatus(com.chibuisi.infrastructure.postgres.enitity.Status.PENDING);

        // Mocking the transformations and repository save operation
        when(taskTransformer.fromTask(task)).thenReturn(taskEntity);
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        when(taskTransformer.fromTaskEntity(taskEntity)).thenReturn(task);

        // Act
        Task result = taskEntityService.saveTask(task);

        // Assert
        assertEquals(task, result);
    }
}

