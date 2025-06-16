package main;

import model.*;
import service.TaskService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class TaskManagerApp {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Task Manager ===");

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
}
