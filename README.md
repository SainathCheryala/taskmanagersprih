# 📝 Task Management System

A simple Java-based Task Manager application built with clean code principles, modular structure, and Java 8+ features.

## 🚀 Features

- Create a task with title, description, due date, priority, and status
- Update task details
- Delete tasks by ID
- List all tasks with optional filtering
- In-memory storage (no DB)
- Designed for extensibility and testing

---

## 🛠️ Requirements

- Java 8 or later
- Command Line (or VS Code with Java Extension Pack)

---

## 📦 Project Structure

```
TASKMANAGERSPRIH/
│
├── src/                     # Source files
│   ├── model/               # Task model classes
│   ├── service/             # Business logic and services
│   ├── exception/           # Custom exceptions
│   └── main/                # Application entry point
│
├── test/                    # Unit and integration tests
│
├── out/                     # Compiled class files (output directory)
│
├── lib/                     # External libraries (if any)
│
└── README.md                # Project documentation
```

---

## 💻 Run Instructions

### 1️⃣ Compile the Application

To compile the application source files, run:

```bash
javac -cp "lib/*" -d out src/**/*.java
```

### 2️⃣ Compile the Tests

To compile the test files along with the application source files, run:

```bash
javac -cp "lib/*" -d out src/**/*.java test/**/*.java
```

### 3️⃣ Run the Application

```bash
java -cp out main.TaskManagerApp
```

✅ You will see:

=== Task Manager ===
Options: [1] Create [2] Update [3] Delete [4] List [5] Exit

---

### 🌐 REST API

The application provides a REST API interface. After running the application, the API will be available at:

```
http://localhost:8080/tasks
```

#### Endpoints

- `GET /tasks`: List all tasks
- `POST /tasks`: Create a new task

#### Example: Create a Task

Use `curl` or a browser to create a task by passing parameters in the URL:

```bash
curl -X POST "http://localhost:8080/tasks?title=NewTask&description=TaskDescription&dueDate=2023-12-31&priority=HIGH&status=PENDING"
```

This will create a new task and return its details.

---

### 🧪 Run Tests

To execute the unit tests, ensure you have the JUnit platform console standalone jar in the `lib/` directory. Then run:

```bash
java -jar lib/junit-platform-console-standalone-1.13.1.jar \
  --class-path out \
  --select-class TaskServiceTest
```

✅ You will see test results indicating the success or failure of each test case.

---

