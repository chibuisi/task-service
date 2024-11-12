package com.chibuisi.domain.service;

import com.chibuisi.domain.model.Task;

public interface TaskService {
    Task saveTask(Task task);

    Task getTask(Long id);

    Task updateTask(Task task, Long id);

    void deleteTask(Long id);
}
