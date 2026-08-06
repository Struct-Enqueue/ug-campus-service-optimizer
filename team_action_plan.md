# Team Action Plan & Assignments

## Campus Smart Service Operations Optimizer -- University of Ghana, Legon
### DCIT 204/308 -- Data Structures & Algorithms

> **Status**: Project skeleton is ready. All 55 files are created and compiling. It's time to assign work and start coding!

---

## What Has Already Been Done

| Done | Details |
|------|---------|
| YES | Maven project setup -- `pom.xml` with Java 17, PostgreSQL driver, JUnit 5 |
| YES | 6 model classes -- Location, Route, Resource, ServiceRequest, AlgorithmRun, AuditEvent |
| YES | 14 data structure stubs -- DynamicArray through DisjointSet (all empty, method signatures defined) |
| YES | 6 graph stubs -- Graph, BFS, DFS, Dijkstra, Prim, Kruskal |
| YES | 4 algorithm stubs -- SearchAlgorithms, SortAlgorithms, Greedy, DynamicProgramming |
| YES | 3 database stubs -- DatabaseConnector, CSVLoader, SchemaSetup |
| YES | 3 service stubs -- RequestDispatcher, SchedulingEngine, IndexingEngine |
| YES | Console menu + Main -- ConsoleMenu.java, Main.java |
| YES | 4 CSV data files -- 60 locations, 110 routes, 35 resources, 310 requests |
| YES | PostgreSQL schema -- `docs/schema.sql` with 6 tables ready to run |
| YES | 14 test stubs -- One per data structure + graph + sort |
| YES | README -- Build instructions, folder map, Git workflow |

---

## Team Assignments (15 Members)

> **IMPORTANT: Fill in real names below.** Replace "Member A", "Member B", etc. with your teammates' actual names before sharing this document with the group.

---

### [CRITICAL] Feature 1 -- Data Loader (START FIRST -- everyone depends on this!)

| Role | Assigned To |
|------|-------------|
| **Lead** | Member A |
| **Support** | Member B |

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| DatabaseConnector.java | `src/main/java/com/ug/campusops/db/` | Implement `getConnection()`, `closeConnection()`, `executeQuery()`, `executeUpdate()` using JDBC |
| CSVLoader.java | `src/main/java/com/ug/campusops/db/` | Read each CSV file line-by-line, parse fields, INSERT into the correct table |
| SchemaSetup.java | `src/main/java/com/ug/campusops/db/` | Execute `CREATE TABLE` SQL (read from `schema.sql` or hard-code), implement `dropTables()` and `seedFromCSV()` |

#### Your Deliverables Checklist
- [ ] PostgreSQL database `campusops` created and tables set up
- [ ] All 60 locations loaded from CSV -- verify with `SELECT COUNT(*) FROM locations;`
- [ ] All 110 routes loaded -- verify with `SELECT COUNT(*) FROM routes;`
- [ ] All 35 resources loaded -- verify with `SELECT COUNT(*) FROM resources;`
- [ ] All 310 requests loaded -- verify with `SELECT COUNT(*) FROM service_requests;`
- [ ] Can fetch data back out (e.g. `SELECT * FROM locations WHERE location_id = 1;` returns "Commonwealth Hall")
- [ ] Screenshot of pgAdmin or psql showing table counts as proof

#### Priority: CRITICAL -- finish within first 3 days so other teams can use real data

---

### Feature 2 -- Linear Structures

| Role | Assigned To |
|------|-------------|
| **Lead** | Member C |
| **Support** | Member D |

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| DynamicArray.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `insert()`, `get()`, `set()`, `remove()`, `resize()` (double capacity when full) |
| MyLinkedList.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `addFirst()`, `addLast()`, `insertAfter()`, `remove()`, `iterator()` |
| MyStack.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `push()`, `pop()`, `peek()`, `isEmpty()` -- use array or linked list internally |
| MyQueue.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `enqueue()`, `dequeue()`, `peek()` |
| CircularQueue.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `enqueue()`, `dequeue()` with wrap-around using `% capacity` |
| MyDeque.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `addFront()`, `addRear()`, `removeFront()`, `removeRear()` |

#### Your Deliverables Checklist
- [ ] All 6 data structures compile and pass basic tests
- [ ] DynamicArray resize trace printed (show capacity 10 -> 20 -> 40)
- [ ] LinkedList diagram drawn (for report)
- [ ] Stack undo-log demo (push 5 operations, pop to undo)
- [ ] CircularQueue front/rear index trace (show wrap-around)
- [ ] Deque urgent-insertion example (addFront for urgent, addRear for normal)
- [ ] All test methods in test stubs filled in and passing

#### Suggested Split
- **Member C**: DynamicArray, MyStack, CircularQueue + their tests
- **Member D**: MyLinkedList, MyQueue, MyDeque + their tests

---

### Feature 3 -- Trees & Hashing

| Role | Assigned To |
|------|-------------|
| **Lead** | Member E |
| **Support** | Member F |

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| BST.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `insert()`, `search()`, `delete()`, `inorderTraversal()`, `height()` |
| RedBlackTree.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `insert()` with `rotateLeft()`, `rotateRight()`, `fixup()` |
| BTree.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `insert()`, `search()`, `splitChild()` |
| HashTable.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `put()`, `get()`, `remove()` with separate chaining |
| MySet.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `add()`, `contains()`, `union()`, `intersection()` using HashTable |
| MyMap.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `put()`, `get()`, `keys()`, `values()` using HashTable |

#### Your Deliverables Checklist
- [ ] BST search path trace (insert 10 campus locations, search for one, show path)
- [ ] BST sorted inorder output of all inserted items
- [ ] Red-black tree before/after rotation diagrams (draw these for report!)
- [ ] Red-black tree height stays O(log n) compared to plain BST
- [ ] BTree node split trace (insert enough keys to trigger split)
- [ ] HashTable collision statistics at load factors 0.25, 0.5, 0.75, 1.0
- [ ] All tests passing

#### Suggested Split
- **Member E**: BST, RedBlackTree, BTree + their tests
- **Member F**: HashTable, MySet, MyMap + their tests

---

### Feature 4 -- Priority Queue, Graph Core & Disjoint Set

| Role | Assigned To |
|------|-------------|
| **Lead** | Member G |
| **Support** | Member H |

> **WARNING: Feature 5 depends on your Graph class.** Push Graph.java as early as possible, even if incomplete, so the Feature 5 team can start building against it. Agree on method names at the meeting!

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| MyPriorityQueue.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `insert()`, `extractMin()`, `siftUp()`, `siftDown()`, `heapify()` |
| Graph.java | `src/main/java/com/ug/campusops/graph/` | Implement adjacency list + matrix, `addVertex()`, `addEdge()`, `getNeighbors()`, `getWeight()` |
| DisjointSet.java | `src/main/java/com/ug/campusops/datastructures/` | Implement `makeSet()`, `find()` (with path compression), `union()` (by rank) |

#### Your Deliverables Checklist
- [ ] PriorityQueue dispatch order trace (insert 10 requests by urgency, extract in order)
- [ ] Graph loaded with at least 10 campus locations and 15 routes
- [ ] `getNeighbors()` returns correct neighbors for a given location
- [ ] DisjointSet Kruskal connectivity trace
- [ ] All tests passing

#### Suggested Split
- **Member G**: MyPriorityQueue + Graph.java
- **Member H**: DisjointSet + Graph tests + help with Graph adjacency matrix

---

### Feature 5 -- Graph Algorithms

| Role | Assigned To |
|------|-------------|
| **Lead** | Member I |
| **Support** | Member J |

> **NOTE:** You need Feature 4's `Graph.java` to exist first. Start by writing your algorithms against a small hard-coded test graph while waiting for the real Graph class.

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| BFS.java | `src/main/java/com/ug/campusops/graph/` | Implement `traverse()` and `findReachable()` using MyQueue |
| DFS.java | `src/main/java/com/ug/campusops/graph/` | Implement `traverse()` and `findReachable()` using MyStack or recursion |
| Dijkstra.java | `src/main/java/com/ug/campusops/graph/` | Implement `shortestPath()` and `allShortestPaths()` using MyPriorityQueue |
| Prim.java | `src/main/java/com/ug/campusops/graph/` | Implement `minimumSpanningTree()` using MyPriorityQueue |
| Kruskal.java | `src/main/java/com/ug/campusops/graph/` | Implement `minimumSpanningTree()` using DisjointSet |

#### Your Deliverables Checklist
- [ ] BFS trace table and graph diagram (from Commonwealth Hall)
- [ ] DFS trace table showing backtracking
- [ ] Dijkstra distance table + predecessor path (e.g. "Commonwealth -> Balme Library")
- [ ] Prim MST edge list and total cost
- [ ] Kruskal MST edge list and total cost (should match Prim!)
- [ ] All graph tests passing

#### Suggested Split
- **Member I**: BFS, DFS, Dijkstra
- **Member J**: Prim, Kruskal + all graph tests

---

### Feature 6 -- Search & Sort Engine

| Role | Assigned To |
|------|-------------|
| **Solo** | Member K |

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| SearchAlgorithms.java | `src/main/java/com/ug/campusops/algorithms/` | Implement `linearSearch()` and `binarySearch()` |
| SortAlgorithms.java | `src/main/java/com/ug/campusops/algorithms/` | Implement `selectionSort()`, `insertionSort()`, `mergeSort()`, `quickSort()` |

#### Your Deliverables Checklist
- [ ] Binary search precondition test (must state: "array MUST be sorted")
- [ ] Counterexample: binary search on unsorted array -- wrong result
- [ ] Insertion sort trace table (step-by-step for 8 elements)
- [ ] Merge sort or quicksort trace table
- [ ] Performance experiment: time all 4 sorts at sizes 100, 500, 1000, 5000, 10000
- [ ] Stability discussion: which sorts are stable? which are in-place?
- [ ] All test stubs filled in

---

### Feature 7 -- Optimisation Engine

| Role | Assigned To |
|------|-------------|
| **Solo** | Member L |

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| Greedy.java | `src/main/java/com/ug/campusops/algorithms/` | Implement `nearestResourceAssignment()` + write `greedyCounterExample()` |
| DynamicProgramming.java | `src/main/java/com/ug/campusops/algorithms/` | Implement `requestKnapsack()` with tabulation table and solution reconstruction |

#### Your Deliverables Checklist
- [ ] Greedy nearest-resource assignment working on real campus data
- [ ] Greedy **counterexample** with numbers: show greedy total cost vs optimal cost
- [ ] DP tabulation table printed (rows = items, columns = budget)
- [ ] DP solution reconstruction: "selected requests: #5, #12, #27"
- [ ] Memoisation or tabulation discussion for report

---

### Feature 8 -- Service Layer

| Role | Assigned To |
|------|-------------|
| **Lead** | Member M |
| **Support** | Member N |

> **TIP:** Ideally someone from the old **Planning team** takes this, since they already know the step-by-step pseudocode.

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| RequestDispatcher.java | `src/main/java/com/ug/campusops/service/` | `submitRequest()` saves to DB + adds to queue; `assignResource()` finds nearest available person |
| SchedulingEngine.java | `src/main/java/com/ug/campusops/service/` | `scheduleNext()` pulls from priority queue; `addToQueue()` routes urgent to deque front |
| IndexingEngine.java | `src/main/java/com/ug/campusops/service/` | `indexLocations()` builds BST by name; `searchByName()` and `searchByCategory()` |

#### Your Deliverables Checklist
- [ ] Submit a request -- it appears in the database as "pending"
- [ ] Schedule next -- most urgent request is returned
- [ ] Assign resource -- nearest available staff member is found via Dijkstra
- [ ] Index search -- "Commonwealth" returns location ID 1
- [ ] Status updates saved back to database

#### Suggested Split
- **Member M**: RequestDispatcher + SchedulingEngine
- **Member N**: IndexingEngine + wiring everything together

---

### Feature 9 -- Console/UI & Final Integration

| Role | Assigned To |
|------|-------------|
| **Solo** | Member O |

> **CAUTION: This is the LAST feature to finish.** Wait until Features 1-8 are mostly working before doing full integration. You can build the menu structure early, but don't try to wire everything until the other features exist.

#### What You Must Build

| File | Path | What To Do |
|------|------|-----------|
| ConsoleMenu.java | `src/main/java/com/ug/campusops/ui/` | Build the menu loop: read user choice -> call Service Layer -> display result -> repeat |
| Main.java | `src/main/java/com/ug/campusops/` | Wire up: DB connection -> schema setup -> load graph -> load queue -> start menu |

#### Your Deliverables Checklist
- [ ] Menu displays cleanly with all 11 options
- [ ] Each option calls the real Service Layer code
- [ ] Full end-to-end demo works: report request -> schedule -> find route -> view result
- [ ] Program runs without crashing for the oral defense
- [ ] Demo video (5-8 minutes) recorded showing all features

---

## Database Setup Guide (Everyone Must Do This!)

### Step 1: Install PostgreSQL

Download from: **https://www.postgresql.org/download/windows/**

During installation:
- Set password to: `postgres` (or whatever you prefer -- just update `DatabaseConnector.java`)
- Default port: `5432`
- Remember your password!

### Step 2: Create the Database

Open **pgAdmin** (installed with PostgreSQL) or **psql** terminal:

```sql
-- In psql terminal:
CREATE DATABASE campusops;
```

Or in **pgAdmin**:
1. Right-click "Databases" -> Create -> Database
2. Name: `campusops`
3. Click Save

### Step 3: Run the Schema

**Option A -- Using psql terminal:**
```bash
psql -U postgres -d campusops -f docs/schema.sql
```

**Option B -- Using pgAdmin:**
1. Click on `campusops` database
2. Click "Query Tool" (wrench icon)
3. Open file: `docs/schema.sql`
4. Click Execute (or press F5)

### Step 4: Verify Tables Were Created

```sql
-- Run this in psql or pgAdmin Query Tool:
\dt
-- Should show: locations, routes, resources, service_requests, algorithm_runs, audit_events
```

### Step 5: Update Your Password in Code

Open `src/main/java/com/ug/campusops/db/DatabaseConnector.java` and change line 18:

```java
private static final String DB_PASS = "postgres"; // <-- Change to YOUR password
```

### Database Schema -- All 6 Tables

**Table: locations** (60 records)

| Column | Type | Notes |
|--------|------|-------|
| location_id | SERIAL PK | auto-increment |
| name | VARCHAR(100) | e.g. "Commonwealth Hall" |
| area | VARCHAR(80) | e.g. "Main Campus Hill" |
| type | VARCHAR(50) | e.g. "Traditional Hall" |
| latitude | DECIMAL(8,4) | GPS coordinate |
| longitude | DECIMAL(8,4) | GPS coordinate |

**Table: routes** (110 records) -- references locations

| Column | Type | Notes |
|--------|------|-------|
| route_id | SERIAL PK | |
| from_location_id | INT FK -> locations | source |
| to_location_id | INT FK -> locations | destination |
| distance_m | INT | meters |
| avg_time_min | INT | minutes |
| traffic_factor | DECIMAL(3,1) | default 1.0 |

**Table: resources** (35 records) -- references locations

| Column | Type | Notes |
|--------|------|-------|
| resource_id | SERIAL PK | |
| type | VARCHAR(50) | e.g. "Electrician", "Plumber" |
| home_location_id | INT FK -> locations | where they are based |
| capacity | INT | default 1 |
| availability_status | VARCHAR(20) | "available" / "busy" |

**Table: service_requests** (310 records) -- references locations + resources

| Column | Type | Notes |
|--------|------|-------|
| request_id | SERIAL PK | |
| source_location_id | INT FK -> locations | where the problem is |
| destination_location_id | INT FK -> locations | where resource goes |
| category | VARCHAR(30) | "electrical", "plumbing", "IT", etc. |
| urgency_level | INT CHECK 1-5 | 1=minor, 5=critical |
| time_submitted | TIMESTAMP | |
| deadline | TIMESTAMP | |
| status | VARCHAR(20) | "pending", "assigned", "completed" |
| assigned_resource_id | INT FK -> resources | |

**Table: algorithm_runs** (for performance experiments)

| Column | Type | Notes |
|--------|------|-------|
| run_id | SERIAL PK | |
| algorithm_name | VARCHAR(50) | e.g. "mergeSort", "dijkstra" |
| input_size | INT | number of elements |
| time_ns | BIGINT | execution time in nanoseconds |
| memory_kb | BIGINT | memory used |
| date_run | TIMESTAMP | |

**Table: audit_events** (for stack-based undo log)

| Column | Type | Notes |
|--------|------|-------|
| event_id | SERIAL PK | |
| event_type | VARCHAR(30) | e.g. "INSERT", "UPDATE" |
| entity_type | VARCHAR(30) | e.g. "request", "resource" |
| entity_id | INT | |
| description | TEXT | |
| timestamp | TIMESTAMP | |

---

## Git Workflow (Quick Reference)

### First Time Setup (Everyone)
```bash
# Clone the repo (one time only)
git clone <your-repo-url>
cd ug-campus-service-optimizer

# Create your feature branch
git checkout -b feature/<feature-name>-<your-name>
# Example: git checkout -b feature/trees-hashing-kofi
```

### Daily Workflow
```bash
# Before starting work -- pull latest changes
git pull origin main

# After making progress -- save your work
git add .
git commit -m "Added BST insert and search methods"
git push origin feature/<your-branch-name>

# When your feature works -- open a Pull Request on GitHub
```

### Branch Naming Convention

| Feature | Branch Name Example |
|---------|-------------------|
| F1 Data Loader | `feature/data-loader-ama` |
| F2 Linear Structures | `feature/linear-structures-kofi` |
| F3 Trees & Hashing | `feature/trees-hashing-abena` |
| F4 PQ/Graph/DisjointSet | `feature/graph-core-kwame` |
| F5 Graph Algorithms | `feature/graph-algos-akua` |
| F6 Search & Sort | `feature/search-sort-yaw` |
| F7 Optimisation | `feature/optimisation-efua` |
| F8 Service Layer | `feature/service-layer-kojo` |
| F9 Console/UI | `feature/console-ui-adjoa` |

### Merge Order (Strict!)
```
Week 1:  F1 (Data Loader) --> merge to main
Week 2:  F2, F3, F4 (parallel) --> merge to main
Week 3:  F5, F6, F7 (parallel) --> merge to main
Week 4:  F8 --> F9 --> final merge
```

---

## Suggested 4-Week Timeline

### Week 1 (Days 1-7) -- Foundation

| Who | Task | Deadline |
|-----|------|----------|
| **All 15 members** | Install Java 17, Maven, PostgreSQL | Day 1 |
| **All 15 members** | Clone repo, create your branch | Day 1 |
| **All 15 members** | Run `mvn compile` -- confirm it works | Day 1 |
| **All 15 members** | Set up PostgreSQL database using guide above | Day 2 |
| **Members A + B** (F1) | Implement DatabaseConnector + CSVLoader | Day 5 |
| **Members A + B** (F1) | Load all CSV data into database, verify counts | Day 7 |
| **All 15 members** | Agree on index-number parameters at meeting | Day 3 |
| **Members G + H** (F4) | Push rough Graph.java (even incomplete) so F5 can start | Day 5 |

### Week 2 (Days 8-14) -- Core Data Structures

| Who | Task | Deadline |
|-----|------|----------|
| **Members C + D** (F2) | All 6 linear structures implemented + tested | Day 14 |
| **Members E + F** (F3) | BST, HashTable, Set, Map implemented + tested | Day 12 |
| **Members E + F** (F3) | RedBlackTree, BTree implemented | Day 14 |
| **Members G + H** (F4) | PriorityQueue, Graph, DisjointSet complete + tested | Day 14 |
| **F1 team** | Merge Data Loader to main | Day 8 |
| **F2, F3, F4 teams** | Open Pull Requests | Day 14 |

### Week 3 (Days 15-21) -- Algorithms

| Who | Task | Deadline |
|-----|------|----------|
| **Members I + J** (F5) | BFS, DFS, Dijkstra, Prim, Kruskal + tests | Day 21 |
| **Member K** (F6) | All search + sort algorithms + tests | Day 21 |
| **Member L** (F7) | Greedy + DP + counterexample | Day 21 |
| **All algorithm teams** | Performance experiments started (timing at different sizes) | Day 21 |
| **F2, F3, F4 teams** | Merge to main | Day 16 |

### Week 4 (Days 22-28) -- Integration & Polish

| Who | Task | Deadline |
|-----|------|----------|
| **Members M + N** (F8) | Service Layer connecting all features | Day 24 |
| **Member O** (F9) | Console menu wired to Service Layer | Day 26 |
| **All teams** | Performance experiments complete + CSV results + graphs | Day 26 |
| **All teams** | Unit tests finalized (40+ tests total) | Day 26 |
| **All teams** | Report sections written | Day 27 |
| **All teams** | Demo video recorded (5-8 minutes) | Day 28 |
| **All teams** | Final merge to main, tag release | Day 28 |

---

## Index Number Parameters -- Fill In At Meeting!

> **IMPORTANT**: Every team member must say their index number out loud. Use the formulas below to calculate your team's unique settings.

### Step 1: Collect Last 3 Digits

| Member | Full Index Number | Last 3 Digits | Last Digit |
|--------|------------------|---------------|------------|
| Member A | _______________ | _______ | ____ |
| Member B | _______________ | _______ | ____ |
| Member C | _______________ | _______ | ____ |
| Member D | _______________ | _______ | ____ |
| Member E | _______________ | _______ | ____ |
| Member F | _______________ | _______ | ____ |
| Member G | _______________ | _______ | ____ |
| Member H | _______________ | _______ | ____ |
| Member I | _______________ | _______ | ____ |
| Member J | _______________ | _______ | ____ |
| Member K | _______________ | _______ | ____ |
| Member L | _______________ | _______ | ____ |
| Member M | _______________ | _______ | ____ |
| Member N | _______________ | _______ | ____ |
| Member O | _______________ | _______ | ____ |

### Step 2: Calculate Settings

| Setting | Formula | Your Value |
|---------|---------|------------|
| **Priority Weight** | (Sum of all "Last Digit" values) % 10 + 1 | _______ |
| **Route Penalty** | (Average of all "Last 3 Digits" values) / 100 | _______ |
| **Hash Table Size** | (Sum of all "Last 3 Digits") % 50 + 20 --> round to nearest prime | _______ |
| **Random Seed** (optional) | Join first digit of each index number | _______ |

### Step 3: Update the Code

Once calculated, update these two places:
1. `HashTable.java` line 30: change `DEFAULT_TABLE_SIZE = 37` to your calculated prime
2. `README.md` index number table: fill in your real values

---

## Team Leader's Master Checklist

### Before the first meeting:
- [ ] Share this document with all 15 members
- [ ] Fill in real names in the assignment table above
- [ ] Ensure everyone has access to the GitHub repository

### At the first meeting:
- [ ] Everyone installs Java 17 + Maven + PostgreSQL
- [ ] Everyone clones the repo and runs `mvn compile`
- [ ] Collect index numbers and calculate the 3 parameters
- [ ] Agree on method names for Graph: `getNeighbors()`, `addEdge()`, `getWeight()`
- [ ] Confirm each person knows which files they own

### Weekly check-ins:
- [ ] Week 1: F1 merged? Database working? Everyone has their branch?
- [ ] Week 2: F2, F3, F4 have Pull Requests open? Tests passing?
- [ ] Week 3: F5, F6, F7 done? Performance experiments started?
- [ ] Week 4: F8 + F9 integrated? Demo works end-to-end? Report drafted?

### Before submission:
- [ ] 40+ unit tests passing (`mvn test`)
- [ ] 6 trace tables completed (binary search, insertion sort, merge/quick sort, Dijkstra, Kruskal/Prim, DP)
- [ ] 3 proof sketches written
- [ ] 2 counterexamples documented (greedy failure + unsorted binary search)
- [ ] Performance CSV + graphs produced for all 6 experiments
- [ ] Database has all records + algorithm_runs populated
- [ ] Demo video recorded (5-8 min)
- [ ] Report compiled (PDF + DOCX)
- [ ] Development log submitted
- [ ] Every member can explain 1 data structure + 1 algorithm for oral defense
