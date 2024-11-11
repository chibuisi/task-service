package com.chibuisi.api.transformer;

import com.chibuisi.api.model.TaskDto;
import com.chibuisi.api.model.UpdateTaskDto;
import com.chibuisi.domain.model.Task;
import jakarta.inject.Singleton;

import java.time.Instant;

@Singleton
public class TaskTransformer {
    public Task fromDto(TaskDto taskDto) {
        return Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .createdTime(taskDto.getCreatedTime() == null ? Instant.now() : taskDto.getCreatedTime())
                .updatedTime(taskDto.getUpdatedTime() == null ? Instant.now() : taskDto.getUpdatedTime())
                .status(taskDto.getStatus())
                .build();
    }

    public TaskDto fromTask(Task task) {
        if(task == null) {
            return null;
        }
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdTime(task.getCreatedTime())
                .updatedTime(task.getUpdatedTime())
                .status(task.getStatus())
                .build();
    }

    public Task fromUpdateTaskDto(UpdateTaskDto updateTaskDto) {
        return Task.builder()
                .title(updateTaskDto.getTitle())
                .description(updateTaskDto.getDescription())
                .status(updateTaskDto.getStatus())
                .build();
    }
}
