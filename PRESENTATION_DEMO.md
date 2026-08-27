# Presentation & Demo Guide

This file is a concise reference for presenting the UG Campus Smart Service Operations Optimizer.
It contains a file-correlation map, the exact commands to run the console demo on real CSV data, verification queries, and short talking points.

---

## 1) At-a-glance file correlation

- `Main.java` — application entry; opens DB, runs `SchemaSetup.seedFromCSV()`, loads graph, starts `ConsoleMenu`.
- `db/` (Feature 1) — `DatabaseConnector.java`, `SchemaSetup.java`, `CSVLoader.java` — load CSVs into Postgres and provide DB access.
- `ui/ConsoleMenu.java` (Feature 9) — interactive menu; calls `RequestDispatcher` (persist requests) and graph algorithms for routes.
- `service/` (Feature 8) — `RequestDispatcher.java`, `SchedulingEngine.java`, `IndexingEngine.java` — dispatch logic, queues, and indexes.
- `graph/` (Features 4 & 5) — `Graph.java`, `Dijkstra.java`, `Prim.java`, `Kruskal.java`, `BFS.java`, `DFS.java` — routing and MST algorithms.
- `datastructures/` (Features 2 & 3) — custom DS: queues, stacks, BST, RedBlackTree, HashTable, DisjointSet, MyPriorityQueue.
- `algorithms/` (Features 6 & 7) — `SortAlgorithms.java`, `SearchAlgorithms.java`, `Greedy.java`, `DynamicProgramming.java`.

> Flow: ConsoleMenu → Service Layer → DB/Graph → Algorithms/DataStructures

---

## 2) Demo prep (one-time on presenter machine)

Follow these steps on the presenter PC so you can `git clone` and run with minimal setup.

A) Clone the repository (replace `<repo-url>`):

```powershell
git clone <repo-url>
cd ug-campus-service-optimizer
```

B) Prerequisites on the presenter PC
- Java 17 JDK on PATH
- Maven on PATH (only if you will build on the presenter PC)
- (Optional) PostgreSQL if you want to use the real DB; otherwise the app must be configured to a reachable DB

C) Create DB and apply schema (if using Postgres on presenter PC)

```powershell
# create DB
psql -U postgres -c "CREATE DATABASE campusops;"
# apply schema
psql -U postgres -d campusops -f docs/schema.sql
```

If `psql` is not in PATH use its full path, for example:

```powershell
"C:\Program Files\PostgreSQL\15\bin\psql.exe" -U postgres -d campusops -f docs/schema.sql
```

D) Build and collect dependencies (on presenter PC if you didn't ship a packaged release):

```powershell
mvn -DskipTests dependency:copy-dependencies package
```

E) If you prefer to ship DB state from your dev machine, create a SQL dump on your dev machine and include it in the repo or share it with the presenter:

Create dump (dev machine):

```powershell
# pg_dump requires proper privileges and connection parameters
pg_dump -U <dbuser> -h <dbhost> -p <dbport> -d campusops -f campusops_dump.sql
```

Restore on the presenter PC:

```powershell
# create the database then restore
psql -U <dbuser> -c "CREATE DATABASE campusops;"
psql -U <dbuser> -d campusops -f campusops_dump.sql
```

F) Quick note: do NOT push `target/` to the repo. Push `src/`, `pom.xml`, `data/`, `docs/schema.sql`, `PRESENTATION_DEMO.md`, and any DB dump you want to share.

G) (Optional) Align `service_requests` sequence if you previously saw duplicate-key errors:

```sql
SELECT setval(pg_get_serial_sequence('service_requests','request_id'), (SELECT COALESCE(MAX(request_id),0) FROM service_requests)+1);
```

---

## 3) Demo run (script to execute live)
0. build first : mvn -DskipTests dependency:copy-dependencies package

1. Start the app:

```powershell
java -cp "target/classes;target/dependency/*" com.ug.campusops.Main
```

2. When the app prints table verification counts, mention: "This confirms our real CSVs loaded: locations, routes, resources, requests."

3. Follow this interactive script (copy/paste inputs):

- Menu: `1`  (Report a new service request)
  - `Enter location ID:` → type a real id from `data/locations.csv` (e.g. `1`)
  - `Enter category:` → e.g. `electrical`
  - `Enter urgency level:` → e.g. `3`
- Menu: `2`  (View pending requests) — show the submitted request in list
- Menu: `3`  (Process next request) — shows assignment result and updates DB
- Menu: `4`  (Find fastest route) — enter `startId` and `destId` to demo Dijkstra
- Menu: `11` (View queue status)
- Menu: `0`  (Exit)

Notes: the demo uses your real CSV data; submitted requests will be persisted to the `service_requests` table.

---

## 4) Verification SQL (open in a second terminal or pgAdmin)

```sql
SELECT COUNT(*) FROM locations;
SELECT COUNT(*) FROM routes;
SELECT COUNT(*) FROM resources;
SELECT COUNT(*) FROM service_requests;
-- show recent requests and assigned resources
SELECT * FROM service_requests ORDER BY request_id DESC LIMIT 5;
SELECT * FROM resources WHERE availability_status = 'busy' ORDER BY resource_id DESC LIMIT 5;
```

---

## 5) Short talking points (one-liners to use during demo)

- Startup: "We load real campus CSVs into Postgres and show table counts on startup — this proves our system works on real data."
- Data loader: "Feature 1 implements CSV → DB loading and schema management. Other teams use these tables for algorithms and services."
- Dispatch: "Submitting a request saves it and enqueues it; processing pulls the most urgent and assigns the nearest available resource using Dijkstra."
- Algorithms: "Graph algorithms (Dijkstra, Prim, Kruskal) and data-structures (hash tables, red-black tree, priority queue) support the system's core logic."

---

## 6) Troubleshooting (quick fixes)

- If insert fails with duplicate key error, run the sequence-fix SQL above once.
- If DB connectivity fails: check `.env` at project root or update credentials in `DatabaseConnector.java`.
- If menu shows empty graph: ensure `SchemaSetup.seedFromCSV()` ran and `GraphDataLoader` loaded vertices and edges.

---

## 7) Quick presenter checklist

- [ ] PostgreSQL running and `campusops` present
- [ ] `mvn ... package` completed and `target/dependency` exists
- [ ] Run `java -cp "target/classes;target/dependency/*" com.ug.campusops.Main` and confirm verification counts
- [ ] Submit a real request and show it in `psql`/pgAdmin
- [ ] Process a request and show assigned resource in DB
- [ ] Show Dijkstra route for two sample locations

---

If you'd like, I can generate a one-page slide (Markdown or PDF) with these commands and talking points formatted for printing or sharing with your team. Let me know which format you prefer.
