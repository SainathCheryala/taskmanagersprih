package repository;

import model.Task;

import java.util.*;

public class TaskRepository {
    private final Map<String, Task> tasks = new HashMap<>();

    public void save(Task task) {
        tasks.put(task.getId(), task);
    }

    public Optional<Task> findById(String id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public void deleteById(String id) {
        tasks.remove(id);
    }

    public Collection<Task> findAll() {
        return tasks.values();
    }
}