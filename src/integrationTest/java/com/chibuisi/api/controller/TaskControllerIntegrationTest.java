package com.chibuisi.api.controller;

import com.chibuisi.api.model.TaskDto;
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
}
