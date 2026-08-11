# Feature 1 — Data Loader: Handoff Guide for Teammates

## What Was Done (Summary)

Feature 1 is the **database foundation** that every other feature depends on. Here's what was built:

### Files Implemented

| File | What It Does |
|------|-------------|
| **DatabaseConnector.java** | Connects to PostgreSQL using JDBC. Reads credentials from a `.env` file (no hardcoded passwords). Provides `getConnection()`, `executeQuery()`, `executeUpdate()`, and `closeConnection()`. |
| **CSVLoader.java** | Reads the 4 CSV files (`locations.csv`, `routes.csv`, `resources.csv`, `requests.csv`) and inserts all rows into the database using `PreparedStatement` for safety. |
| **SchemaSetup.java** | Creates all 6 database tables + 7 indexes. Can also drop and reseed the entire database with `seedFromCSV("data")`. |
| **Main.java** | Wired up to automatically connect, check if data exists, and load CSVs on first run. Prints a verification report showing row counts. |
| **.env** | Stores database credentials locally (not committed to Git). |
| **.gitignore** | Updated to exclude `.env` so passwords stay private. |

### Database Tables Created

| Table | Records | Purpose |
|-------|---------|---------|
| `locations` | 60 | Every place on campus (halls, departments, gates, etc.) |
| `routes` | 110 | Paths connecting locations with distance and time |
| `resources` | 35 | Maintenance staff, shuttles, vehicles |
| `service_requests` | 310 | Maintenance complaints with urgency levels |
| `algorithm_runs` | 0 (empty) | For storing your algorithm performance benchmarks |
| `audit_events` | 0 (empty) | For stack-based undo/audit logging |

---

## What Each Teammate Must Do After Pulling

### Step 1: Install PostgreSQL (if not already installed)
- Download from https://www.postgresql.org/downloads/
- Remember the password you set during installation!
- Note which **port** it runs on (check pgAdmin → right-click your server → Properties → Connection tab)

### Step 2: Create the Database
- Open **pgAdmin**
- Right-click **Databases** → **Create** → **Database**
- Name: `campusops`
- Click **Save**

### Step 3: Create Your `.env` File
In the **project root** (same folder as `pom.xml`), create a file called `.env` with:

```
DB_URL=jdbc:postgresql://localhost:YOUR_PORT/campusops
DB_USER=postgres
DB_PASS="YOUR_POSTGRES_PASSWORD"
```

> **IMPORTANT**: Replace `YOUR_PORT` with your actual port (common values: 5432, 5433, 5434). Replace `YOUR_POSTGRES_PASSWORD` with the password you set when installing PostgreSQL.

> **DO NOT** commit the `.env` file — it's already in `.gitignore`.

### Step 4: Run the App to Load Data
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.ug.campusops.Main"
```

You should see output like:
```
[DB] Loaded credentials from .env file
[DB] Connected to jdbc:postgresql://localhost:XXXX/campusops
[CSV] Loaded 60 locations...
[CSV] Loaded 110 routes...
[CSV] Loaded 35 resources...
[CSV] Loaded 310 service requests...

VERIFICATION — Table Row Counts
  locations            :   60  ✓ PASS
  routes               :  110  ✓ PASS
  resources            :   35  ✓ PASS
  service_requests     :  310  ✓ PASS
```

If you see all 4 PASS, you're good to go!

---

## How to Use the Database in Your Feature

### Reading Data (for Features 2-9)

```java
// 1. Create a connector
DatabaseConnector db = new DatabaseConnector();

// 2. Query data
ResultSet rs = db.executeQuery("SELECT * FROM locations");
while (rs.next()) {
    int id = rs.getInt("location_id");
    String name = rs.getString("name");
    // ... use the data to populate your data structures
}
rs.close();

// 3. Close when done
db.closeConnection();
```

### Writing Data (for algorithm_runs, audit_events)

```java
DatabaseConnector db = new DatabaseConnector();
db.executeUpdate(
    "INSERT INTO algorithm_runs (algorithm_name, input_size, time_ns) "
    + "VALUES ('BubbleSort', 1000, 45000)"
);
db.closeConnection();
```

### Resetting the Database (if data gets corrupted)

```java
SchemaSetup schema = new SchemaSetup(new DatabaseConnector());
schema.seedFromCSV("data");  // drops everything, recreates tables, reloads CSVs
```

---

## Testing Checklist for Each Feature Team

### Before You Start Coding
- [ ] Pull the latest code from Git
- [ ] Create your `.env` file (see Step 3 above)
- [ ] Run `Main.java` and confirm all 4 table counts show ✓ PASS
- [ ] Open pgAdmin and verify you can see data: `SELECT * FROM locations LIMIT 5;`

### While Coding Your Feature
- [ ] Use `DatabaseConnector` to fetch real data (don't hardcode test data)
- [ ] Test with real campus data from the database
- [ ] If you need to reset, call `schema.seedFromCSV("data")`

### Before Pushing Your Feature
- [ ] Make sure `mvn compile` succeeds with no errors
- [ ] Run your JUnit tests: `mvn test`
- [ ] Make sure you did NOT commit the `.env` file
- [ ] Make sure your code doesn't break `Main.java`

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `database "campusops" does not exist` | Create it in pgAdmin (Step 2) or check your port in `.env` |
| `password authentication failed` | Your `.env` password doesn't match your PostgreSQL password. Run `ALTER USER postgres PASSWORD 'yourpassword';` in pgAdmin to reset it |
| `Connection refused` | PostgreSQL isn't running. Start it from Windows Services or pgAdmin |
| Tables exist but are empty | Run `Main.java` — it auto-loads CSVs when tables are empty |
| Data is corrupted | Use `schema.seedFromCSV("data")` to do a full reset |
