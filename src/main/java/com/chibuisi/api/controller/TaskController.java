package com.chibuisi.api.controller;

import com.chibuisi.api.model.TaskDto;
import com.chibuisi.api.model.UpdateTaskDto;
import com.chibuisi.api.transformer.TaskTransformer;
import com.chibuisi.domain.service.TaskService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
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
            description = "Creates a task with the provided data",
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

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Retrieves a task",
            description = "Retrieves a task with the provided id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task retrieved successfully",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request"
                    )
            }
    )
    public HttpResponse<TaskDto> getTask(@PathVariable Long id) {
        TaskDto result = taskTransformer.fromTask(taskService.getTask(id));

        return HttpResponse.ok(result);
    }

    @Put("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Updates a task",
            description = "Updates a task with the provided data and id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateTaskDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request"
                    )
            }
    )
    public HttpResponse<TaskDto> updateTask(@Body @Valid UpdateTaskDto updateTaskDto, @PathVariable Long id) {
        return HttpResponse.ok(
                taskTransformer.fromTask(taskService.updateTask(taskTransformer.fromUpdateTaskDto(updateTaskDto), id))
        );
    }

    @Delete("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Deletes a task",
            description = "Deletes a task with the provided id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Task updated successfully",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request"
                    )
            }
    )
    public HttpResponse<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return HttpResponse.noContent();
    }

}
