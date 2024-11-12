package com.chibuisi.infrastructure.service;

import com.chibuisi.api.exception.InvalidTaskException;
import com.chibuisi.api.exception.TaskNotFoundException;
import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.model.TaskStatus;
import com.chibuisi.domain.transformer.TaskTransformer;
import com.chibuisi.infrastructure.postgres.enitity.TaskEntity;
import com.chibuisi.infrastructure.postgres.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        verify(taskRepository, times(1)).save(taskEntity);
        verify(taskTransformer, times(1)).fromTaskEntity(taskEntity);
    }

    @Test
    void getTask_WhenTaskExists_ReturnsTransformedTask() {
        // Arrange
        Long taskId = 1L;
        TaskEntity taskEntity = new TaskEntity();
        Task task = Task.builder()
                .title("Sample Task")
                .description("Sample Description")
                .createdTime(Instant.parse("2024-11-06T23:22:30.913109Z"))
                .status(TaskStatus.PENDING)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskTransformer.fromTaskEntity(taskEntity)).thenReturn(task);

        // Act
        Task result = taskEntityService.getTask(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskTransformer, times(1)).fromTaskEntity(taskEntity);
    }

    @Test
    void getTask_ShouldThrowTaskNotFoundException_WhenTaskNotFound() {
        // Arrange
        Long taskId = 20L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskEntityService.getTask(taskId));

        verify(taskRepository, times(1)).findById(taskId);
        verifyNoInteractions(taskTransformer);
    }

    @Test
    void updateTask_ShouldThrowInvalidTaskException_WhenTaskIsNull() {
        // Arrange
        Long taskId = 1L;

        // Act & Assert
        Exception exception = assertThrows(InvalidTaskException.class, () -> taskEntityService.updateTask(null, taskId));
        assertEquals("Task cannot be null", exception.getMessage());

        verifyNoInteractions(taskRepository, taskTransformer);
    }

    @Test
    void updateTask_ShouldThrowInvalidTaskException_WhenIdIsNull() {
        // Arrange
        Task task = Task.builder().build();

        // Act & Assert
        Exception exception = assertThrows(InvalidTaskException.class, () -> taskEntityService.updateTask(task, null));
        assertEquals("ID cannot be null", exception.getMessage());

        verifyNoInteractions(taskRepository, taskTransformer);
    }

    @Test
    void updateTask_ShouldThrowTaskNotFoundException_WhenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;
        Task task = Task.builder().build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskEntityService.updateTask(task, taskId));

        verify(taskRepository, times(1)).findById(taskId);
        verifyNoInteractions(taskTransformer);
    }

    @Test
    void updateTask_ShouldUpdateTaskSuccessfully_WhenTaskAndIdAreValid() {
        // Arrange
        Long taskId = 1L;
        Task task = Task.builder()
                .title("Sample Task")
                .description("Sample Description")
                .createdTime(Instant.parse("2024-11-06T23:22:30.913109Z"))
                .status(TaskStatus.PENDING)
                .build();
        TaskEntity existingTaskEntity = new TaskEntity();
        TaskEntity updatedTaskEntity = new TaskEntity();
        TaskEntity savedTaskEntity = new TaskEntity();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTaskEntity));
        when(taskTransformer.updateTaskEntityFromTask(existingTaskEntity, task)).thenReturn(updatedTaskEntity);
        when(taskRepository.update(updatedTaskEntity)).thenReturn(savedTaskEntity);
        when(taskTransformer.fromTaskEntity(savedTaskEntity)).thenReturn(task);

        // Act
        Task result = taskEntityService.updateTask(task, taskId);

        // Assert
        assertEquals(task, result);
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskTransformer, times(1)).updateTaskEntityFromTask(existingTaskEntity, task);
        verify(taskRepository, times(1)).update(updatedTaskEntity);
        verify(taskTransformer, times(1)).fromTaskEntity(savedTaskEntity);
    }

    @Test
    public void deleteTask_shouldCallDeleteById_onTaskRepository() {
        // Given
        Long taskId = 1L;

        // When
        taskEntityService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void deleteTask_whenIdIsNull_shouldThrowException() {
        // Given
        Long taskId = null;

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> taskEntityService.deleteTask(taskId));
    }
}

