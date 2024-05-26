package com.example.demo.controllers;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Task;
import com.example.demo.services.TaskServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskServiceInterface taskServiceInterface;

    public TaskController(TaskServiceInterface taskServiceInterface) {
        this.taskServiceInterface = taskServiceInterface;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createTask(@Valid @RequestBody Task task) throws Exception {
        try{
            Optional<Task> createdTask = taskServiceInterface.createNewTask(task);
            if(createdTask.isEmpty()) {
                throw new Exception("Unable to add new task");
            }
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTasks() throws Exception {
        try{
            List<Task> tasks = taskServiceInterface.getAllTasks();
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("No task(s) found");
            }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findTaskById(@PathVariable("id") Long id) throws Exception {
        try {
            Optional<Task> task = taskServiceInterface.findTaskById(id);
            if (task.isEmpty()) {
                throw new ResourceNotFoundException(id.toString());
            }
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<Object> getAllCompletedTasks() throws Exception{
        try{
            List<Task> tasks = taskServiceInterface.findAllCompletedTask();
            if(tasks.isEmpty()){
                throw new ResourceNotFoundException("Completed task(s) not found");
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/incomplete")
    public ResponseEntity<Object> getAllIncompleteTasks() throws Exception{
        try{
            List<Task> tasks = taskServiceInterface.findAllInCompleteTask();
            if(tasks.isEmpty()) {
                throw new ResourceNotFoundException("Incomplete task(s) not found");
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable("id") Long id) throws Exception {
        try{
            boolean removed = taskServiceInterface.deleteTask(id);
            if (!removed) {
                throw new Exception("Task not removed or not found");
            }
            return new ResponseEntity<>("Task deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable ("id") Long id, @Valid @RequestBody Task task) {
        try{
            Optional<Task> result = taskServiceInterface.updateTask(id, task);
            if(result.isEmpty()){
                throw new ResourceNotFoundException("Task not updated or not found");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
