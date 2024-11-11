package com.chibuisi.domain.transformer;

import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.model.TaskStatus;
import com.chibuisi.infrastructure.postgres.enitity.Status;
import com.chibuisi.infrastructure.postgres.enitity.TaskEntity;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

import java.time.Instant;

@Singleton
public class TaskTransformer {
    public TaskEntity fromTask(Task task){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setCreatedAt(task.getCreatedTime());
        taskEntity.setUpdatedAt(Instant.now());
        taskEntity.setStatus(Status.valueOf(task.getStatus().name()));
        return taskEntity;
    }

    public Task fromTaskEntity(TaskEntity taskEntity) {
        return Task.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .createdTime(taskEntity.getCreatedAt())
                .updatedTime(taskEntity.getUpdatedAt())
                .status(TaskStatus.valueOf(taskEntity.getStatus().name()))
                .build();
    }

    public TaskEntity updateTaskEntityFromTask(TaskEntity taskEntity, Task task) {
        taskEntity.setUpdatedAt(Instant.now());
        if (!StringUtils.isEmpty(task.getTitle()))
            taskEntity.setTitle(task.getTitle());
        if(!StringUtils.isEmpty(task.getDescription()))
            taskEntity.setDescription(task.getDescription());
        taskEntity.setStatus(Status.valueOf(task.getStatus().name()));
        return taskEntity;
    }


}
