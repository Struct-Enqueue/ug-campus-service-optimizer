package com.ug.campusops.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Creates (and optionally drops) the PostgreSQL database tables.
 * Run this once when setting up the system for the first time.
 *
 * Feature 1 — Data Loader
 *
 * Tables created: locations, routes, resources, service_requests, algorithm_runs, audit_events
 * See docs/schema.sql for the full SQL.
 */
public class SchemaSetup {

    private DatabaseConnector dbConnector;

    /**
     * Creates a SchemaSetup that uses the given database connector.
     *
     * @param dbConnector the database connector
     */
    public SchemaSetup(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    /**
     * Creates all required database tables if they don't already exist.
     * Uses CREATE TABLE IF NOT EXISTS for safety.
     */
    public void createTables() {
        System.out.println("[Schema] Creating tables...");

        try {
            // 1. Locations
            dbConnector.executeUpdate(
                "CREATE TABLE IF NOT EXISTS locations ("
                + "location_id SERIAL PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "area VARCHAR(80), "
                + "type VARCHAR(50), "
                + "latitude DECIMAL(8, 4), "
                + "longitude DECIMAL(8, 4))"
            );

            // 2. Routes
            dbConnector.executeUpdate(
                "CREATE TABLE IF NOT EXISTS routes ("
                + "route_id SERIAL PRIMARY KEY, "
                + "from_location_id INT NOT NULL REFERENCES locations(location_id), "
                + "to_location_id INT NOT NULL REFERENCES locations(location_id), "
                + "distance_m INT NOT NULL, "
                + "avg_time_min INT NOT NULL, "
                + "traffic_factor DECIMAL(3, 1) DEFAULT 1.0)"
            );

            // 3. Resources
            dbConnector.executeUpdate(
                "CREATE TABLE IF NOT EXISTS resources ("
                + "resource_id SERIAL PRIMARY KEY, "
                + "type VARCHAR(50) NOT NULL, "
                + "home_location_id INT REFERENCES locations(location_id), "
                + "capacity INT DEFAULT 1, "
                + "availability_status VARCHAR(20) DEFAULT 'available')"
            );

            // 4. Service Requests
            dbConnector.executeUpdate(
                "CREATE TABLE IF NOT EXISTS service_requests ("
                + "request_id SERIAL PRIMARY KEY, "
                + "source_location_id INT NOT NULL REFERENCES locations(location_id), "
                + "destination_location_id INT REFERENCES locations(location_id), "
                + "category VARCHAR(30) NOT NULL, "
                + "urgency_level INT NOT NULL CHECK (urgency_level BETWEEN 1 AND 5), "
                + "time_submitted TIMESTAMP NOT NULL DEFAULT NOW(), "
                + "deadline TIMESTAMP, "
                + "status VARCHAR(20) DEFAULT 'pending', "
                + "assigned_resource_id INT REFERENCES resources(resource_id))"
            );

            // 5. Algorithm Runs
            dbConnector.executeUpdate(
                "CREATE TABLE IF NOT EXISTS algorithm_runs ("
                + "run_id SERIAL PRIMARY KEY, "
                + "algorithm_name VARCHAR(50) NOT NULL, "
                + "input_size INT NOT NULL, "
                + "time_ns BIGINT NOT NULL, "
                + "memory_kb BIGINT, "
                + "date_run TIMESTAMP DEFAULT NOW())"
            );

            // 6. Audit Events
            dbConnector.executeUpdate(
                "CREATE TABLE IF NOT EXISTS audit_events ("
                + "event_id SERIAL PRIMARY KEY, "
                + "event_type VARCHAR(30) NOT NULL, "
                + "entity_type VARCHAR(30), "
                + "entity_id INT, "
                + "description TEXT, "
                + "timestamp TIMESTAMP DEFAULT NOW())"
            );

            // Indexes
            dbConnector.executeUpdate("CREATE INDEX IF NOT EXISTS idx_requests_status ON service_requests(status)");
            dbConnector.executeUpdate("CREATE INDEX IF NOT EXISTS idx_requests_urgency ON service_requests(urgency_level)");
            dbConnector.executeUpdate("CREATE INDEX IF NOT EXISTS idx_requests_location ON service_requests(source_location_id)");
            dbConnector.executeUpdate("CREATE INDEX IF NOT EXISTS idx_routes_from ON routes(from_location_id)");
            dbConnector.executeUpdate("CREATE INDEX IF NOT EXISTS idx_routes_to ON routes(to_location_id)");
            dbConnector.executeUpdate("CREATE INDEX IF NOT EXISTS idx_resources_status ON resources(availability_status)");
            dbConnector.executeUpdate("CREATE INDEX IF NOT EXISTS idx_algorithm_runs_name ON algorithm_runs(algorithm_name)");

            System.out.println("[Schema] All 6 tables and 7 indexes created successfully.");

        } catch (SQLException e) {
            System.err.println("[Schema] Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Drops all tables. USE WITH CAUTION — this deletes all data.
     * Useful for resetting during development/testing.
     * Drops in reverse order to respect foreign key constraints.
     */
    public void dropTables() {
        System.out.println("[Schema] Dropping all tables...");

        // Drop in reverse dependency order to avoid FK constraint errors
        String[] tables = {
            "audit_events",
            "algorithm_runs",
            "service_requests",
            "resources",
            "routes",
            "locations"
        };

        try {
            for (String table : tables) {
                dbConnector.executeUpdate("DROP TABLE IF EXISTS " + table + " CASCADE");
                System.out.println("[Schema]   Dropped: " + table);
            }
            System.out.println("[Schema] All tables dropped.");
        } catch (SQLException e) {
            System.err.println("[Schema] Error dropping tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Convenience method: drops all tables, recreates them, then seeds from CSV.
     * Full database reset + reload.
     *
     * @param dataDir path to the data/ directory containing the CSV files
     */
    public void seedFromCSV(String dataDir) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   Campus Ops — Full Database Reset      ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Step 1: Drop existing tables
        dropTables();

        // Step 2: Recreate all tables
        createTables();

        // Step 3: Load data from CSV files
        CSVLoader loader = new CSVLoader(dbConnector);
        loader.loadAll(dataDir);

        System.out.println("\n[Schema] seedFromCSV complete. Database is ready!");
    }
}
