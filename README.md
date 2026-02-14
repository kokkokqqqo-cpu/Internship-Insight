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

---

## Bonus Task — Caching Layer (Simple In-Memory Cache)

### Objective
Enhance application performance by implementing a simple **in-memory cache** for frequently accessed data.  
The cache prevents repeated database queries when the same endpoint is called multiple times without changes.

---

## Requirements Mapping (Rubric → Implementation)

### 1) Implement a simple in-memory cache to store frequently requested data
**Implemented:** `SimpleCache` stores cached values in memory using a `ConcurrentHashMap`.

- **Storage type:** `ConcurrentMap<String, Object>`
- **Why in memory:** Data is stored inside the JVM process (no external cache server).
- **Why ConcurrentHashMap:** Web applications handle multiple requests in parallel; `ConcurrentHashMap` is safe for concurrent access.

**Key operations provided:**
- `put(key, value)` — store a value
- `get(key)` — retrieve value
- `contains(key)` — check if key exists
- `clear()` — clear entire cache
- `getOrCompute(key, supplier)` — read-through caching helper

---

### 2) Cache the result of at least one commonly used method
**Cached method:** the service method that returns all internships.

- Endpoint: `GET /api/internships`
- Service method: `InternshipService#getAll()`

**Reason:** This is a common high-read operation. Without caching, each request triggers a database query, even when the data has not changed.

---

### 3) Ensure repeated calls return cached data instead of querying the database again
**Behavior:** `getAll()` uses the cache key `"allInternships"`.

- First call:
  - cache miss → load from DB → store result → return result
- Second and later calls:
  - cache hit → return cached list without DB query

To prove this behavior during review, console logs are added:

- On cache miss: `Returning from DATABASE`
- On cache hit: `Returning from CACHE`

This makes it easy to demonstrate that the database is not queried repeatedly.

---

### 4) Properly implement Singleton pattern to manage the cache instance
**Implemented:** `SimpleCache` uses the Singleton pattern.

- The class has:
  - a private constructor (prevents `new SimpleCache()` from outside)
  - a single static instance created once
  - a public `getInstance()` method that returns the same instance every time

**Why Singleton here:**  
A cache must be shared across service calls and HTTP requests while the application is running. If each service created its own cache, the cached data would not be reused and performance would not improve.

---

### 5) Provide a mechanism to clear/invalidate the cache
This project implements **both** options for completeness.

#### A) Automatic invalidation (after create/update/delete)
Cache is cleared after write operations on internships:

- `create(...)` → `cache.clear()`
- `update(...)` → `cache.clear()`
- `delete(...)` → `cache.clear()`

**Why:**  
The cached list of internships becomes outdated after any modification, so clearing the cache ensures the next `GET /api/internships` returns fresh data.

#### B) Manual cache clearing endpoint
A dedicated endpoint exists to clear the cache manually:

- `DELETE /api/cache/clear`

This is helpful for:
- demos during grading
- debugging during development
- forcing refresh without restarting the server

---

## Design Constraints Compliance

### Cache stored in memory (Map)
 Uses a Map-like structure (`ConcurrentHashMap`) stored in JVM memory.

### Only one cache instance (Singleton)
 Singleton pattern ensures one cache instance for the whole application runtime.

### SOLID principles
 Separation of concerns:
- Cache responsibilities are isolated in `SimpleCache`
- Business logic stays in the service layer
- Controllers remain thin and only expose endpoints

 Thread-safety consideration:
- `ConcurrentHashMap` is used so concurrent requests do not corrupt the cache.

### Does not break layered architecture
 The cache does not bypass repositories or mix concerns:
- Repository is still the only layer that talks to the DB.
- Service decides when to use cached values.
- Controller remains a transport layer.

---

## How the Cache Works (Step-by-Step)

### Cached Key
For internships list:
- Cache key: `allInternships`

### Read-through caching (`getOrCompute`)
`getOrCompute(key, supplier)` means:

1. If the key exists in cache → return cached value
2. If not → compute it using `supplier` (e.g., DB call), store it, then return it

This pattern makes caching easy and prevents duplicated boilerplate code.

---

## How to Test the Bonus Cache (Postman)

### Step 1 — First request hits DB
- `GET http://localhost:8080/api/internships`

Expected in console:
Returning from DATABASE

### Step 2 — Second request hits cache
- Repeat:
  - `GET http://localhost:8080/api/internships`

Expected in console:
Returning from CACHE

### Step 3 — Manually clear cache
- `DELETE http://localhost:8080/api/cache/clear`

### Step 4 — After clear, DB is used again
- `GET http://localhost:8080/api/internships`

Expected in console:
Returning from DATABASE

---

## Design Constraints Compliance

- **In-memory storage:** Uses a Map-based structure (`ConcurrentHashMap`)
- **Singleton:** Only one cache instance exists (Singleton pattern)
- **SOLID principles:** Cache logic is isolated in its own component and reused where needed
- **Layered architecture:** Controllers remain thin; caching is applied in the service layer without breaking controller/service/repository boundaries
