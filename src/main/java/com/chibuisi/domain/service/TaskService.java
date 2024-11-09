package com.chibuisi.domain.service;

import com.chibuisi.domain.model.Task;

public interface TaskService {
    Task saveTask(Task task);

    Task getTask(Long id);

//    Task updateTask(Long id);
//
//    Task deleteTask(Long id);
}
