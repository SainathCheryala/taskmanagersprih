package util;

import model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskFilter {
    public static List<Task> filterTasks(List<Task> tasks, Optional<Status> status, Optional<Priority> priority,
                                         Optional<LocalDate> from, Optional<LocalDate> to) {
        return tasks.stream()
                .filter(t -> !status.isPresent() || t.getStatus() == status.get())
                .filter(t -> !priority.isPresent() || t.getPriority() == priority.get())
                .filter(t -> !from.isPresent() || (t.getDueDate() != null && !t.getDueDate().isBefore(from.get())))
                .filter(t -> !to.isPresent() || (t.getDueDate() != null && !t.getDueDate().isAfter(to.get())))
                .collect(Collectors.toList());
    }
}