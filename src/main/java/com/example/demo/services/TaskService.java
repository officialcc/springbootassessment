package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements TaskServiceInterface{

    @Autowired
    TaskRepository taskRepository;

    public void TaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> createNewTask(Task task) {
        try{
            // Try to insert a task
            Task newTask = taskRepository.save(task);
            return Optional.of(newTask);
        } catch (Exception e) {
            // Otherwise return empty object
            return Optional.empty();
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findAllCompletedTask() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllInCompleteTask() {
        return taskRepository.findByCompletedFalse();
    }

    @Override
    public boolean deleteTask(Long id) {
        try{
            // Find the Task to delete by id
            Optional<Task> task = taskRepository.findById(id);
            if(task.isEmpty()) {
                return false;
            }
            taskRepository.delete(task.get());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<Task> updateTask(Long id, Task task) {
        try {
            // Find the task to update by id
            Optional<Task> toUpdate = taskRepository.findById(id);
            if(toUpdate.isEmpty()) {
                return Optional.empty();
            }
            Task updateTask = toUpdate.get();
            updateTask.setTask(task.getTask());
            updateTask.setCompleted(task.isCompleted());

            Task updatedTask = taskRepository.save(updateTask);
            return Optional.of(updatedTask);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
