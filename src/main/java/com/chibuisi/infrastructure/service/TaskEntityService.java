package com.chibuisi.infrastructure.service;

import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.service.TaskService;
import com.chibuisi.domain.transformer.TaskTransformer;
import com.chibuisi.infrastructure.postgres.enitity.TaskEntity;
import com.chibuisi.infrastructure.postgres.repository.TaskRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

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

    @Override
    public Task getTask(Long id) {
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        return optionalTaskEntity.map(taskEntity -> taskTransformer.fromTaskEntity(taskEntity)).orElse(null);
    }
}
