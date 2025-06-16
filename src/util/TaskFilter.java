package util;

import model.Task;
import model.Priority;
import model.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskFilter {
    public static List<Task> filterTasks(List<Task> tasks, Optional<Status> status, Optional<Priority> priority, Optional<LocalDate> from, Optional<LocalDate> to, Optional<String> sortBy, Optional<String> sortOrder) {
        return tasks.stream()
            .filter(task -> status.map(s -> task.getStatus() == s).orElse(true))
            .filter(task -> priority.map(p -> task.getPriority() == p).orElse(true))
            .filter(task -> from.map(f -> !task.getDueDate().isBefore(f)).orElse(true))
            .filter(task -> to.map(t -> !task.getDueDate().isAfter(t)).orElse(true))
            .sorted((task1, task2) -> {
                if (sortBy.isPresent()) {
                    int comparison = 0;
                    switch (sortBy.get().toLowerCase()) {
                        case "duedate":
                            comparison = task1.getDueDate().compareTo(task2.getDueDate());
                            break;
                        case "priority":
                            comparison = task1.getPriority().compareTo(task2.getPriority());
                            break;
                    }
                    if (sortOrder.isPresent() && sortOrder.get().equalsIgnoreCase("desc")) {
                        return -comparison; // Reverse the comparison for descending order
                    }
                    return comparison; // Default is ascending order
                }
                return 0;
            })
            .collect(Collectors.toList());
    }
}