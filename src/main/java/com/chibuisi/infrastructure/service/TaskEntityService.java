package com.chibuisi.infrastructure.service;

import com.chibuisi.api.exception.InvalidTaskException;
import com.chibuisi.api.exception.TaskNotFoundException;
import com.chibuisi.domain.model.Task;
import com.chibuisi.domain.service.TaskService;
import com.chibuisi.domain.transformer.TaskTransformer;
import com.chibuisi.infrastructure.postgres.enitity.TaskEntity;
import com.chibuisi.infrastructure.postgres.repository.TaskRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
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
        return optionalTaskEntity.map(taskEntity ->
                taskTransformer.fromTaskEntity(taskEntity)).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task updateTask(Task task, Long id) {
        if(task == null) {
            throw new InvalidTaskException("Task cannot be null");
        }

        if(id == null) {
            throw new InvalidTaskException("ID cannot be null");
        }

        Optional<TaskEntity> optionalExistingTaskEntity = taskRepository.findById(id);
        if(optionalExistingTaskEntity.isEmpty()) {
            throw new TaskNotFoundException(id);
        }

        TaskEntity existingTaskEntity = optionalExistingTaskEntity.get();
        TaskEntity updatedTaskEntity = taskTransformer.updateTaskEntityFromTask(existingTaskEntity, task);

        TaskEntity taskEntity = taskRepository.update(updatedTaskEntity);

        return taskTransformer.fromTaskEntity(taskEntity);
    }

    @Override
    public void deleteTask(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        taskRepository.deleteById(id);
    }
}
