package service;

import exception.TaskNotFoundException;
import model.*;
import repository.TaskRepository;
import util.TaskFilter;

import java.time.LocalDate;
import java.util.*;

public class TaskService {
    private final TaskRepository repository = new TaskRepository();

    public Task createTask(String title, String description, LocalDate dueDate, Priority priority, Status status) {
        Task task = new Task(title, description, dueDate, priority, status);
        repository.save(task);
        return task;
    }

    public Task updateTask(String id, String title, String description, LocalDate dueDate, Priority priority, Status status) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setStatus(status);
        return task;
    }

    public void deleteTask(String id) {
        if (!repository.findById(id).isPresent()) throw new TaskNotFoundException("Task not found");
        repository.deleteById(id);
    }

    public List<Task> listTasks(Optional<Status> status, Optional<Priority> priority,
                                Optional<LocalDate> from, Optional<LocalDate> to) {
        return TaskFilter.filterTasks(new ArrayList<>(repository.findAll()), status, priority, from, to);
    }

    public Task getTaskById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }
}