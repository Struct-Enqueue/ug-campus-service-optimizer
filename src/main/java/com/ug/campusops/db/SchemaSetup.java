package com.ug.campusops.db;

import java.sql.SQLException;

/**
 * Creates (and optionally drops) the PostgreSQL database tables.
 * Run this once when setting up the system for the first time.
 *
 * Feature 1 — Data Loader
 *
 * Tables created: locations, routes, resources, service_requests,
 * algorithm_runs, audit_events
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
        if (dbConnector == null) {
            throw new IllegalArgumentException("dbConnector must not be null");
        }
        this.dbConnector = dbConnector;
    }

    /**
     * Creates all required database tables if they don't already exist.
     * Uses CREATE TABLE IF NOT EXISTS for safety.
     */
    public void createTables() {
        String[] statements = {
                "CREATE TABLE IF NOT EXISTS locations ("
                        + "location_id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, "
                        + "area VARCHAR(80), type VARCHAR(50), latitude DECIMAL(8,4), "
                        + "longitude DECIMAL(8,4))",
                "CREATE TABLE IF NOT EXISTS routes ("
                        + "route_id SERIAL PRIMARY KEY, from_location_id INT NOT NULL REFERENCES locations(location_id), "
                        + "to_location_id INT NOT NULL REFERENCES locations(location_id), distance_m INT NOT NULL, "
                        + "avg_time_min INT NOT NULL, traffic_factor DECIMAL(3,1) DEFAULT 1.0)",
                "CREATE TABLE IF NOT EXISTS resources ("
                        + "resource_id SERIAL PRIMARY KEY, type VARCHAR(50) NOT NULL, "
                        + "home_location_id INT REFERENCES locations(location_id), capacity INT DEFAULT 1, "
                        + "availability_status VARCHAR(20) DEFAULT 'available')",
                "CREATE TABLE IF NOT EXISTS service_requests ("
                        + "request_id SERIAL PRIMARY KEY, source_location_id INT NOT NULL REFERENCES locations(location_id), "
                        + "destination_location_id INT REFERENCES locations(location_id), category VARCHAR(30) NOT NULL, "
                        + "urgency_level INT NOT NULL CHECK (urgency_level BETWEEN 1 AND 5), "
                        + "time_submitted TIMESTAMP NOT NULL DEFAULT NOW(), deadline TIMESTAMP, "
                        + "status VARCHAR(20) DEFAULT 'pending', assigned_resource_id INT REFERENCES resources(resource_id))",
                "CREATE TABLE IF NOT EXISTS algorithm_runs ("
                        + "run_id SERIAL PRIMARY KEY, algorithm_name VARCHAR(50) NOT NULL, input_size INT NOT NULL, "
                        + "time_ns BIGINT NOT NULL, memory_kb BIGINT, date_run TIMESTAMP DEFAULT NOW())",
                "CREATE TABLE IF NOT EXISTS audit_events ("
                        + "event_id SERIAL PRIMARY KEY, event_type VARCHAR(30) NOT NULL, entity_type VARCHAR(30), "
                        + "entity_id INT, description TEXT, timestamp TIMESTAMP DEFAULT NOW())",
                "CREATE INDEX IF NOT EXISTS idx_requests_status ON service_requests(status)",
                "CREATE INDEX IF NOT EXISTS idx_requests_urgency ON service_requests(urgency_level)",
                "CREATE INDEX IF NOT EXISTS idx_requests_location ON service_requests(source_location_id)",
                "CREATE INDEX IF NOT EXISTS idx_routes_from ON routes(from_location_id)",
                "CREATE INDEX IF NOT EXISTS idx_routes_to ON routes(to_location_id)",
                "CREATE INDEX IF NOT EXISTS idx_resources_status ON resources(availability_status)",
                "CREATE INDEX IF NOT EXISTS idx_algorithm_runs_name ON algorithm_runs(algorithm_name)"
        };
        executeAll(statements);
    }

    /**
     * Drops all tables. USE WITH CAUTION — this deletes all data.
     * Useful for resetting during development/testing.
     */
    public void dropTables() {
        executeAll(new String[] {
                "DROP TABLE IF EXISTS audit_events CASCADE",
                "DROP TABLE IF EXISTS algorithm_runs CASCADE",
                "DROP TABLE IF EXISTS service_requests CASCADE",
                "DROP TABLE IF EXISTS resources CASCADE",
                "DROP TABLE IF EXISTS routes CASCADE",
                "DROP TABLE IF EXISTS locations CASCADE"
        });
    }

    /**
     * Convenience method: drops all tables, recreates them, then seeds from CSV.
     * Full database reset + reload.
     *
     * @param dataDir path to the data/ directory containing the CSV files
     */
    public void seedFromCSV(String dataDir) {
        if (dataDir == null || dataDir.isBlank()) {
            throw new IllegalArgumentException("dataDir must not be blank");
        }
        dropTables();
        createTables();
        new CSVLoader(dbConnector).loadAll(dataDir);
    }

    private void executeAll(String[] statements) {
        try {
            for (String statement : statements) {
                dbConnector.executeUpdate(statement);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not update database schema", exception);
        }
    }
}
