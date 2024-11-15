package com.chibuisi.api.controller;

import com.chibuisi.api.model.TaskDto;
import com.chibuisi.api.transformer.TaskTransformer;
import com.chibuisi.domain.service.TaskService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Controller("/task")
public class TaskController {

    @Inject
    private TaskService taskService;
    @Inject
    private TaskTransformer taskTransformer;

    public TaskController(TaskService taskService, TaskTransformer taskTransformer) {
        this.taskService = taskService;
        this.taskTransformer = taskTransformer;
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Create a new task",
            description = "Creates a task with the provided details",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Task created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request"
                    )
            }
    )
    public HttpResponse<TaskDto> saveTask(@Body @Valid TaskDto taskDto) {
        return HttpResponse.created(
                taskTransformer.fromTask(taskService.saveTask(taskTransformer.fromDto(taskDto))));
    }

}
