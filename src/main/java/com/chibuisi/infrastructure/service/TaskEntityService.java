package com.chibuisi.infrastructure.service;

import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.service.TaskService;
import com.chibuisi.domain.transformer.TaskTransformer;
import com.chibuisi.infrastructure.postgres.repository.TaskRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class TaskEntityService implements TaskService {
    @Inject
    private TaskRepository taskRepository;

    @Inject
    private TaskTransformer taskTransformer;

    public TaskEntityService(TaskRepository taskRepository, TaskTransformer taskTransformer){
        this.taskRepository = taskRepository;
        this.taskTransformer = taskTransformer;
    }

    @Override
    public Task saveTask(Task task) {
        return taskTransformer.fromTaskEntity(taskRepository.save(taskTransformer.fromTask(task)));
    }
}
