package com.chibuisi.api.controller;

import com.chibuisi.api.exception.TaskNotFoundException;
import com.chibuisi.api.model.TaskDto;
import com.chibuisi.api.model.UpdateTaskDto;
import com.chibuisi.domain.model.TaskStatus;
import com.chibuisi.support.ApiTestSupport;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

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

        TaskDto postResultTaskDto = response.getBody(TaskDto.class)
                .orElseThrow(() -> new RuntimeException("TaskDto response body is empty"));

        // Assert
        assertEquals(201, response.getStatus().getCode());
        assertNotNull(response.body());
        assertNotNull(response.body().getId());
        assertEquals(response.body().getId(), postResultTaskDto.getId());
        assertEquals("Integration Test Task", response.body().getTitle());

        HttpRequest<TaskDto> getRequest = HttpRequest.GET(String.format("task/%d", postResultTaskDto.getId()));

        HttpResponse<TaskDto> getResponse = client.toBlocking().exchange(getRequest, Argument.of(TaskDto.class));

        TaskDto resultTaskDto = getResponse.getBody(TaskDto.class)
                .orElseThrow(() -> new RuntimeException("TaskDto response body is empty"));

        assertEquals(200, getResponse.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals(resultTaskDto.getId(), postResultTaskDto.getId());
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

        TaskDto postResultTaskDto = response.getBody(TaskDto.class)
                .orElseThrow(() -> new RuntimeException("TaskDto response body is empty"));

        // Assert
        assertEquals(201, response.getStatus().getCode());
        assertNotNull(response.body());
        assertNotNull(response.body().getId());
        assertEquals(response.body().getId(), postResultTaskDto.getId());
        assertEquals("Integration Test Task", response.body().getTitle());

        HttpRequest<TaskDto> getRequest = HttpRequest.GET(String.format("task/%d", postResultTaskDto.getId()));

        HttpResponse<TaskDto> getResponse = client.toBlocking().exchange(getRequest, Argument.of(TaskDto.class));

        assertEquals(200, getResponse.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals(response.body().getId(), postResultTaskDto.getId());

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
        assertEquals(updateResponse.body().getId(), postResultTaskDto.getId());
        assertEquals(updateResponse.body().getTitle(), updateTaskDto.getTitle());
        assertEquals(updateResponse.body().getStatus(), updateTaskDto.getStatus());
        assertEquals(updateResponse.body().getDescription(), updateTaskDto.getDescription());

        log.info("Passed update task integration test");
    }

    @Test
    void testDeleteTask() {
        log.info("Running delete task integration test");
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

        TaskDto postResultTaskDto = response.getBody(TaskDto.class)
                .orElseThrow(() -> new RuntimeException("TaskDto response body is empty"));

        // Assert
        assertEquals(201, response.getStatus().getCode());
        assertNotNull(response.body());
        assertNotNull(response.body().getId());
        assertEquals(response.body().getId(), postResultTaskDto.getId());
        assertEquals("Integration Test Task", response.body().getTitle());

        HttpRequest<TaskDto> getRequest = HttpRequest.GET(String.format("task/%d", postResultTaskDto.getId()));

        HttpResponse<TaskDto> getResponse = client.toBlocking().exchange(getRequest, Argument.of(TaskDto.class));

        TaskDto resultTaskDto = getResponse.getBody(TaskDto.class)
                .orElseThrow(() -> new RuntimeException("TaskDto response body is empty"));

        assertEquals(200, getResponse.getStatus().getCode());
        assertNotNull(response.body());
        assertEquals(resultTaskDto.getId(), postResultTaskDto.getId());

        HttpRequest<Void> deleteRequest = HttpRequest.DELETE(String.format("task/%d", postResultTaskDto.getId()));

        HttpResponse<Void> deleteResponse = client.toBlocking().exchange(deleteRequest, Argument.of(Void.class));

        assertEquals(204, deleteResponse.getStatus().getCode());

        HttpRequest<TaskDto> getRequest1 = HttpRequest.GET(String.format("task/%d", postResultTaskDto.getId()));

        assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(getRequest1, Argument.of(TaskDto.class));
        });

        log.info("Passed get task integration test");


    }

}
