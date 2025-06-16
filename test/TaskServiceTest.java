import model.*;
import org.junit.jupiter.api.*;
import service.TaskService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
    private TaskService service;

    @BeforeEach
    public void setUp() {
        service = new TaskService();
    }

    @Test
    public void testCreateTask() {
        Task task = service.createTask("Test", "Description", LocalDate.now(), Priority.HIGH, Status.PENDING);
        assertNotNull(task.getId());
        assertEquals("Test", task.getTitle());
    }

    @Test
    public void testUpdateTask() {
        Task task = service.createTask("Initial", "", null, Priority.LOW, Status.PENDING);
        Task updated = service.updateTask(task.getId(), "Updated", "New Desc", LocalDate.now(), Priority.MEDIUM, Status.IN_PROGRESS);
        assertEquals("Updated", updated.getTitle());
    }

    @Test
    public void testDeleteTask() {
        Task task = service.createTask("To Delete", "", null, Priority.MEDIUM, Status.PENDING);
        service.deleteTask(task.getId());
        assertThrows(RuntimeException.class, () -> service.getTaskById(task.getId()));
    }

    @Test
    public void testListTasksWithFilter() {
        service.createTask("T1", "", LocalDate.now(), Priority.LOW, Status.COMPLETED);
        service.createTask("T2", "", LocalDate.now(), Priority.HIGH, Status.PENDING);
        var results = service.listTasks(Optional.of(Status.COMPLETED), Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals(1, results.size());
    }

    @Test
    public void testCreateTaskWithInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> 
            service.createTask("", "Description", LocalDate.now(), Priority.HIGH, Status.PENDING)
        );
    }

    @Test
    public void testListTasksWithoutFilter() {
        service.createTask("Task1", "Desc1", LocalDate.now(), Priority.LOW, Status.COMPLETED);
        service.createTask("Task2", "Desc2", LocalDate.now(), Priority.HIGH, Status.PENDING);
        var results = service.listTasks(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals(2, results.size());
    }

    @Test
    public void testUpdateNonExistentTask() {
        assertThrows(RuntimeException.class, () -> 
            service.updateTask("invalid-id", "Title", "Desc", LocalDate.now(), Priority.MEDIUM, Status.IN_PROGRESS)
        );
    }

    @Test
    public void testDeleteNonExistentTask() {
        assertThrows(RuntimeException.class, () -> service.deleteTask("invalid-id"));
    }
}
