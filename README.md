# Internship Insight

### Spring Boot REST API

## Project Overview

**Internship Insight** is a Spring Boot REST API developed as an endterm university project.
The system connects students and companies through internship applications.

Students create profiles, add skills, and apply for internships.
Companies create internship offers and receive ranked applications.
Each application receives a score based on skill matching.

The project demonstrates backend development, clean architecture, design patterns, and database integration.

---

## System Architecture

The application follows a layered architecture:

```
Client (Postman)
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
PostgreSQL Database
```

### Controller

Handles HTTP requests and returns JSON responses.

### Service

Contains business logic, validation, and score calculation.

### Repository

Communicates with the database using JPA and Hibernate.

### Database

Stores all system data in PostgreSQL.

This structure keeps the system modular, readable, and easy to maintain.

---

## Core Entities

### Student

Stores personal information, experience, and skills.
Students can apply for internships.

### Company

Represents an employer.
Companies create internships and review applications.

### Skill

Stored in a separate table.
Used in many-to-many relationships with students and internships.

### Internship

Belongs to a company.
Contains:

* Type (PAID / UNPAID)
* Deadline
* Available seats
* Required skills

### Application

Connects a student and an internship.

Two types:

* **PaidApplication** (salary + motivation)
* **UnpaidApplication** (motivation only)

Each application stores:

* Creation date
* Score
* Application type

---

## Business Logic

When a student applies:

1. The system checks if the student exists.
2. The system checks if the internship exists.
3. The system checks available seats.
4. The correct application type is created.
5. A score is calculated based on skill matching.
6. The application is saved.
7. Available seats are reduced.

All logic is handled in the service layer.

---

## Ranking System

Applications for one internship are sorted by score.
Higher score means better skill match.

Companies use this ranking to select candidates.

---

## Design Patterns

### Singleton

Used for score calculation.
Ensures one shared instance.

### Factory

Creates `PaidApplication` or `UnpaidApplication` based on internship type.
Improves flexibility and clean object creation.

### Builder

Used to construct complex objects with many fields.
Improves readability and maintainability.

---

## Component Principles

The project follows:

* **REP** – Reusable components are separated.
* **CCP** – Classes that change together are grouped together.
* **CRP** – No unnecessary dependencies between modules.


## REST API

The system follows REST principles:

* `GET` – Retrieve data
* `POST` – Create data
* `PUT` – Update data
* `DELETE` – Delete data

All communication uses JSON.
Endpoints are tested using Postman.

---

## Error Handling

Global exception handling is implemented.

The API returns clear error messages for:

* Missing entities
* Invalid input
* Business rule violations

This ensures stability and reliability.

---

## Technologies

* Java
* Spring Boot
* Hibernate & JPA
* PostgreSQL
* Maven
* Postman

---

## Conclusion

Internship Insight demonstrates:

* Clean layered architecture
* SOLID principles
* Design pattern implementation
* RESTful API development
* Database integration

The system is modular, structured, and easy to extend.
It satisfies all university endterm project requirements and reflects professional backend standards.
