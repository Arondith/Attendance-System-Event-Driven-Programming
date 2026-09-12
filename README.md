# Attendance System — Event-Driven Programming

## About

A **Java desktop attendance-management system** developed for Event-Driven Programming coursework. The project demonstrates how an event-driven GUI application can manage attendance records, academic information, administrative users, and reporting from one desktop interface.

The system includes an **attendance-scanning screen**, attendance reports, user administration, college and department management, course entry and maintenance, and multiple Java Swing/NetBeans forms. It was designed as a practical academic exercise in handling button events, form interactions, data entry, validation, navigation between screens, and database-connected desktop workflows.

## Core features

- Attendance scanning interface
- Attendance report generation and viewing
- Administrative user management
- College management
- Department entry and management
- Course entry and course management
- Event-driven buttons, forms, and navigation
- Java desktop GUI built with form definitions
- Academic data organization
- Database-ready application structure

## Project structure

The repository contains Java source files and GUI form definitions. Some of the main files include:

- `AttendanceScannerScreen.java` — attendance scanning interface
- `AttendanceReportScreen.java` — attendance reporting
- `AdminAddUser.java` — administrative user management
- `CollegesForm.java` — college management
- `CollegeDepartmentEntryForm.java` — department entry and management
- `CourseEntryForm.java` — course entry
- `CoursesForm.java` — course management

Corresponding `.form` files contain the GUI form definitions used by the Java desktop application.

## Technology

- Java
- Java Swing / NetBeans GUI forms
- Event-driven programming
- Desktop application development
- Database-connected workflow concepts

## Running locally

1. Clone or download the repository.
2. Open the project in a compatible Java IDE such as Apache NetBeans.
3. Ensure the required Java Development Kit is installed.
4. Configure any database connection settings required by the application.
5. Build and run the project from the IDE.

## Project scope

This repository is intended as an academic demonstration of event-driven desktop application development. Before adapting it for production use, review authentication, database configuration, input validation, error handling, logging, and backup requirements for the target environment.

## Contributing

Small improvements are welcome when maintaining or extending the project. Keep changes focused and easy to review:

1. Describe the problem or improvement clearly in the commit message.
2. Avoid committing IDE-generated files unless they are required by the NetBeans project.
3. Test affected forms and navigation after changing Java source or `.form` files.
4. Never commit passwords, database credentials, API keys, or other secrets.

## Notes

This is an academic project. Review environment-specific configuration and database credentials before using it outside a local development environment.
