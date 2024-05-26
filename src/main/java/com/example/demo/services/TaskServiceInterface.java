package com.example.demo.services;

import com.example.demo.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskServiceInterface {

    public abstract Optional<Task> createNewTask(Task task);
    public abstract List<Task> getAllTasks();
    public abstract Optional<Task> findTaskById(Long id);
    public abstract List<Task> findAllCompletedTask();
    public abstract List<Task> findAllInCompleteTask();
    public abstract boolean deleteTask(Long id);
    public abstract Optional<Task> updateTask(Long id, Task task);

}
