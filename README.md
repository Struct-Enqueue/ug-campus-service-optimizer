# UG Campus Smart Service Operations Optimizer

**University of Ghana, Legon — DCIT 204/308 Data Structures & Algorithms**

A Smart Service Operations Optimizer for the University of Ghana, Legon campus. The system handles maintenance requests (electrical, plumbing, IT, cleaning, structural), dispatches resources, finds fastest routes across campus, and evaluates algorithm performance — all backed by custom data structures.

---

## Prerequisites

| Tool | Version | Purpose |
|------|---------|---------|
| **Java JDK** | 17+ | Core language |
| **Maven** | 3.8+ | Build tool (compiles, tests, manages dependencies) |
| **PostgreSQL** | 14+ | Database |

### Installing Maven (if you don't have it)

**Windows:**
1. Download from https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Apache\maven`
3. Add `C:\Program Files\Apache\maven\bin` to your system PATH
4. Verify: `mvn --version`

**Or** if you have Chocolatey: `choco install maven`

### Setting up PostgreSQL

1. Install PostgreSQL from https://www.postgresql.org/download/
2. Open pgAdmin or psql terminal
3. Create the database:
   ```sql
   CREATE DATABASE campusops;
   ```
4. Run the schema:
   ```bash
   psql -U postgres -d campusops -f docs/schema.sql
   ```
5. Update the password in `DatabaseConnector.java` if yours is different from `postgres`

---

## Quick Start

```bash
# Clone the repository (one time)
git clone <your-repo-url>
cd ug-campus-service-optimizer

# Compile everything
mvn compile

# Run tests
mvn test

# Run the program
mvn exec:java -Dexec.mainClass="com.ug.campusops.Main"
# Or:
java -cp target/classes com.ug.campusops.Main
```

---

## Project Structure

```
ug-campus-service-optimizer/
├── pom.xml                           Maven config (dependencies, build settings)
├── src/main/java/com/ug/campusops/
│   ├── Main.java                     Entry point
│   ├── model/                        Data classes (Location, Route, Resource, etc.)
│   ├── datastructures/               Custom data structures (NO built-in Java ones!)
│   │   ├── DynamicArray.java
│   │   ├── MyLinkedList.java
│   │   ├── MyStack.java
│   │   ├── MyQueue.java
│   │   ├── CircularQueue.java
│   │   ├── MyDeque.java
│   │   ├── MyPriorityQueue.java
│   │   ├── BST.java
│   │   ├── RedBlackTree.java
│   │   ├── BTree.java
│   │   ├── HashTable.java
│   │   ├── MySet.java
│   │   ├── MyMap.java
│   │   └── DisjointSet.java
│   ├── graph/                        Graph representation & algorithms
│   │   ├── Graph.java
│   │   ├── BFS.java
│   │   ├── DFS.java
│   │   ├── Dijkstra.java
│   │   ├── Prim.java
│   │   └── Kruskal.java
│   ├── algorithms/                   Search, sort, greedy, DP
│   │   ├── SearchAlgorithms.java
│   │   ├── SortAlgorithms.java
│   │   ├── Greedy.java
│   │   └── DynamicProgramming.java
│   ├── db/                           Database connector & CSV loader
│   │   ├── DatabaseConnector.java
│   │   ├── CSVLoader.java
│   │   └── SchemaSetup.java
│   ├── service/                      Business logic layer
│   │   ├── RequestDispatcher.java
│   │   ├── SchedulingEngine.java
│   │   └── IndexingEngine.java
│   └── ui/
│       └── ConsoleMenu.java          Console menu for demos
├── src/test/java/...                 Unit tests (one per data structure)
├── data/                             CSV seed data
│   ├── locations.csv                 60 UG campus locations
│   ├── routes.csv                    110 route segments
│   ├── resources.csv                 35 maintenance staff
│   └── requests.csv                  310 service requests
└── docs/
    └── schema.sql                    PostgreSQL table definitions
```

---

## Feature → Folder Mapping

| Feature | Team Size | Folder(s) | Key Files |
|---------|-----------|-----------|-----------|
| **F1** Data Loader | 1–2 | `db/`, `data/` | DatabaseConnector, CSVLoader, SchemaSetup |
| **F2** Linear Structures | 2 | `datastructures/` | DynamicArray, MyLinkedList, MyStack, MyQueue, CircularQueue, MyDeque |
| **F3** Trees & Hashing | 2 | `datastructures/` | BST, RedBlackTree, BTree, HashTable, MySet, MyMap |
| **F4** PQ, Graph, DisjointSet | 2 | `datastructures/`, `graph/` | MyPriorityQueue, Graph, DisjointSet |
| **F5** Graph Algorithms | 2 | `graph/` | BFS, DFS, Dijkstra, Prim, Kruskal |
| **F6** Search & Sort | 1–2 | `algorithms/` | SearchAlgorithms, SortAlgorithms |
| **F7** Optimisation | 1–2 | `algorithms/` | Greedy, DynamicProgramming |
| **F8** Service Layer | 2 | `service/` | RequestDispatcher, SchedulingEngine, IndexingEngine |
| **F9** Console/UI | 2 | `ui/`, `Main.java` | ConsoleMenu, Main |

---

## How to Implement Your Feature

Every stub file has:
- **Method signatures** already defined — don't rename them
- **Javadoc** explaining what the method should do
- **`// TODO: Feature X team`** markers showing it's your job
- **`throw UnsupportedOperationException`** — replace this with your actual code

Example:
```java
public void push(T element) {
    // TODO: Feature 2 team — implement this
    throw new UnsupportedOperationException("MyStack.push() not yet implemented");
}
```
→ Replace the `throw` line with your actual implementation.

---

## Git Workflow

1. **Create your branch**: `git checkout -b feature/trees-hashing-kofi`
2. **Work only in your folder(s)** — don't edit other features' files
3. **Commit often**: `git commit -m "Added BST insert and search methods"`
4. **Push regularly**: `git push origin feature/trees-hashing-kofi`
5. **Open a Pull Request** when a piece works
6. **Never push directly to main**

### Merge Order
```
F1 (Data Loader) → F2, F3, F4 (parallel) → F5, F6, F7 (parallel) → F8 → F9
```

---

## Index Number Parameters

> **TODO**: Fill in with your team's actual index numbers at your next meeting.

| Setting | Where Used | Current Value | Formula |
|---------|-----------|---------------|---------|
| Priority Weight | Request scheduling | `TBD` | Last digit sum % 10 + 1 |
| Route Penalty | Dijkstra weighting | `TBD` | Last-3-digit average / 100 |
| Hash Table Size | HashTable default | `37` (placeholder) | Last-3-digit sum % 50 + 20 → nearest prime |

---

## Dataset Summary

| Entity | Records | File |
|--------|---------|------|
| Locations | 60 | `data/locations.csv` |
| Routes | 110 | `data/routes.csv` |
| Resources | 35 | `data/resources.csv` |
| Requests | 310 | `data/requests.csv` |