package main;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import model.*;
import service.TaskService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class TaskManagerApp {
    public static void main(String[] args) throws IOException {
        TaskService service = new TaskService();
        Object lock = new Object(); // Lock object for synchronization
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Task Manager ===");

        // Start REST API server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tasks", new TaskHandler(service, lock));
        server.setExecutor(null);
        server.start();
        System.out.println("REST API running at http://localhost:8080/tasks");

        while (true) {
            System.out.println("Options: [1] Create [2] Update [3] Delete [4] List [5] Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();
                    LocalDate due = null;
                    while (true) {
                        System.out.print("Enter due date (YYYY-MM-DD): ");
                        String dateInput = scanner.nextLine();
                        if (dateInput.isEmpty()) {
                            break;
                        }
                        try {
                            due = LocalDate.parse(dateInput);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        }
                    }
                    System.out.print("Enter priority (LOW/MEDIUM/HIGH): ");
                    Priority p = Priority.valueOf(scanner.nextLine().toUpperCase());
                    Task task = service.createTask(title, desc, due, p, Status.PENDING);
                    System.out.println("Task created: " + task);
                    break;
                case "2":
                    System.out.print("Enter task ID: ");
                    String updateId = scanner.nextLine();
                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("New description: ");
                    String newDesc = scanner.nextLine();
                    LocalDate newDue = null;
                    while (true) {
                        System.out.print("New due date (YYYY-MM-DD): ");
                        String newDate = scanner.nextLine();
                        if (newDate.isEmpty()) {
                            break;
                        }
                        try {
                            newDue = LocalDate.parse(newDate);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        }
                    }
                    System.out.print("New priority: ");
                    Priority newPriority = Priority.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("New status: ");
                    Status newStatus = Status.valueOf(scanner.nextLine().toUpperCase());
                    service.updateTask(updateId, newTitle, newDesc, newDue, newPriority, newStatus);
                    System.out.println("Task updated.");
                    break;
                case "3":
                    System.out.print("Enter task ID to delete: ");
                    String delId = scanner.nextLine();
                    service.deleteTask(delId);
                    System.out.println("Deleted.");
                    break;
                case "4":
                    System.out.print("Sort by (duedate/priority): ");
                    String sortBy = scanner.nextLine();
                    System.out.print("Sort order (asc/desc): ");
                    String sortOrder = scanner.nextLine();
                    System.out.println("Listing all tasks:");
                    service.listTasks(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(sortBy), Optional.of(sortOrder))
                            .forEach(System.out::println);
                    break;
                case "5":
                    System.out.println("Exiting.");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    static class TaskHandler implements HttpHandler {
        private final TaskService service;
        private final Object lock;

        public TaskHandler(TaskService service, Object lock) {
            this.service = service;
            this.lock = lock;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            synchronized (lock) { // Synchronize access to the service
                if ("GET".equals(exchange.getRequestMethod())) {
                    response = service.listTasks(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())
                            .toString();
                } else if ("POST".equals(exchange.getRequestMethod())) {
                    String query = exchange.getRequestURI().getQuery();
                    String[] params = query != null ? query.split("&") : new String[0];
                    String title = null, description = null;
                    LocalDate dueDate = null;
                    Priority priority = null;
                    Status status = Status.PENDING;

                    for (String param : params) {
                        String[] keyValue = param.split("=");
                        switch (keyValue[0]) {
                            case "title":
                                title = keyValue[1];
                                break;
                            case "description":
                                description = keyValue[1];
                                break;
                            case "dueDate":
                                dueDate = LocalDate.parse(keyValue[1]);
                                break;
                            case "priority":
                                priority = Priority.valueOf(keyValue[1].toUpperCase());
                                break;
                            case "status":
                                status = Status.valueOf(keyValue[1].toUpperCase());
                                break;
                        }
                    }

                    if (title != null && priority != null) {
                        Task task = service.createTask(title, description, dueDate, priority, status);
                        response = "Task created: " + task.toString();
                    } else {
                        response = "Invalid input. Title and priority are required.";
                    }
                }
            }
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
