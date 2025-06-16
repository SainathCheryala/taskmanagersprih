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

Open your terminal in the project root directory and run:

### 1️⃣ Compile the Java files

```bash
javac -d out src/model/*.java src/service/*.java src/exception/*.java src/main/*.java
```

### 2️⃣ Run the application

```bash
java -cp out main.TaskManagerApp
```

✅ You will see:

=== Task Manager ===
Options: [1] Create [2] Update [3] Delete [4] List [5] Exit

---

