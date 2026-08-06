package com.ug.campusops.db;

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
        // TODO: Feature 1 team — implement this
        //   Execute the SQL from docs/schema.sql using dbConnector.executeUpdate()
        //   You can either read the file or hard-code the CREATE TABLE statements here
        throw new UnsupportedOperationException("SchemaSetup.createTables() not yet implemented");
    }

    /**
     * Drops all tables. USE WITH CAUTION — this deletes all data.
     * Useful for resetting during development/testing.
     */
    public void dropTables() {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("SchemaSetup.dropTables() not yet implemented");
    }

    /**
     * Convenience method: drops all tables, recreates them, then seeds from CSV.
     * Full database reset + reload.
     *
     * @param dataDir path to the data/ directory containing the CSV files
     */
    public void seedFromCSV(String dataDir) {
        // TODO: Feature 1 team — implement this
        //   1. dropTables()
        //   2. createTables()
        //   3. new CSVLoader(dbConnector).loadAll(dataDir)
        throw new UnsupportedOperationException("SchemaSetup.seedFromCSV() not yet implemented");
    }
}
