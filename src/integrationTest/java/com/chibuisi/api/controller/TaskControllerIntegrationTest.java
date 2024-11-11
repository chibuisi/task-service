package com.chibuisi.api.controller;

import com.chibuisi.api.model.TaskDto;
import com.chibuisi.api.model.UpdateTaskDto;
import com.chibuisi.domain.model.TaskStatus;
import com.chibuisi.support.ApiTestSupport;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class TaskControllerIntegrationTest extends ApiTestSupport  {
    @Test
    void testCreateTask() {
        log.info("Running create task integration test");
        // Arrange
        TaskDto taskDto = TaskDto.builder()
                .title("Integration Test Task")
                .description("This task is created in an integration test")
                .status(TaskStatus.PENDING)
                .createdTime(Instant.now())
                .updatedTime(Instant.now())
                .build();

        HttpRequest<TaskDto> request = HttpRequest.POST("/task", taskDto);

        // Act
        HttpResponse<TaskDto> response = client.toBlocking().exchange(request, Argument.of(TaskDto.class));

        // Assert
        assertEquals(201, response.getStatus().getCode());
        assertNotNull(response.body());
        assertNotNull(response.body().getId());
        assertEquals("Integration Test Task", response.body().getTitle());
        log.info("Passed create task integration test");
    }
    @Test
    void testGetTask() {
        log.info("Running get task integration test");
        // Arrange
        TaskDto taskDto = TaskDto.builder()
                .title("Integration Test Task")
                .description("This task is created in an integration test")
                .status(TaskStatus.PENDING)
                .createdTime(Instant.now())
                .updatedTime(Instant.now())
                .build();

        HttpRequest<TaskDto> request = HttpRequest.POST("/task", taskDto);

        // Act
        HttpResponse<TaskDto> response = client.toBlocking().exchange(request, Argument.of(TaskDto.class));

        // Assert
        assertEquals(201, response.getStatus().getCode());
        assertNotNull(response.body());
        assertNotNull(response.body().getId());
        assertEquals("Integration Test Task", response.body().getTitle());

        HttpRequest<TaskDto> getRequest = HttpRequest.GET(String.format("task/%d", 1L));

        HttpResponse<TaskDto> getResponse = client.toBlocking().exchange(getRequest, Argument.of(TaskDto.class));
        assertEquals(200, getResponse.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals(response.body().getId(), 1L);
        log.info("Passed get task integration test");
    }
    @Test
    void testUpdateTask() {
        log.info("Running update task integration test");
        // Arrange
        TaskDto taskDto = TaskDto.builder()
                .title("Integration Test Task")
                .description("This task is created in an integration test")
                .status(TaskStatus.PENDING)
                .createdTime(Instant.now())
                .updatedTime(Instant.now())
                .build();

        HttpRequest<TaskDto> request = HttpRequest.POST("/task", taskDto);

        // Act
        HttpResponse<TaskDto> response = client.toBlocking().exchange(request, Argument.of(TaskDto.class));

        // Assert
        assertEquals(201, response.getStatus().getCode());
        assertNotNull(response.body());
        assertNotNull(response.body().getId());
        assertEquals("Integration Test Task", response.body().getTitle());

        HttpRequest<TaskDto> getRequest = HttpRequest.GET(String.format("task/%d", 1L));

        HttpResponse<TaskDto> getResponse = client.toBlocking().exchange(getRequest, Argument.of(TaskDto.class));
        assertEquals(200, getResponse.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals(response.body().getId(), 1L);

        TaskDto resultTaskDto = getResponse.getBody(TaskDto.class)
                .orElseThrow(() -> new RuntimeException("TaskDto response body is empty"));

        UpdateTaskDto updateTaskDto = UpdateTaskDto.builder()
                .title("New Sample").status(TaskStatus.IN_PROGRESS).description("Description").build();

        HttpRequest<UpdateTaskDto> updateRequest =
                HttpRequest.PUT(String.format("/task/%s", resultTaskDto.getId()), updateTaskDto);
        HttpResponse<TaskDto> updateResponse =
                client.toBlocking().exchange(updateRequest, Argument.of(TaskDto.class));
        assertEquals(200, updateResponse.getStatus().getCode());
        assertNotNull(updateResponse.body());
        assertEquals(updateResponse.body().getId(), 1L);
        assertEquals(updateResponse.body().getTitle(), updateTaskDto.getTitle());
        assertEquals(updateResponse.body().getStatus(), updateTaskDto.getStatus());
        assertEquals(updateResponse.body().getDescription(), updateTaskDto.getDescription());

        log.info("Passed update task integration test");
    }
}
