package util;

import model.Task;
import model.Priority;
import model.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskFilter {
    public static List<Task> filterTasks(List<Task> tasks, Optional<Status> status, Optional<Priority> priority, Optional<LocalDate> from, Optional<LocalDate> to) {
        return tasks.stream()
            .filter(task -> status.map(s -> task.getStatus() == s).orElse(true))
            .filter(task -> priority.map(p -> task.getPriority() == p).orElse(true))
            .filter(task -> from.map(f -> !task.getDueDate().isBefore(f)).orElse(true))
            .filter(task -> to.map(t -> !task.getDueDate().isAfter(t)).orElse(true))
            .collect(Collectors.toList());
    }
}