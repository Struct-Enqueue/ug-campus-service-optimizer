package com.ug.campusops.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the PostgreSQL database connection for the campus operations system.
 * All database access goes through this class.
 *
 * Feature 1 — Data Loader
 *
 * SETUP: Ensure PostgreSQL is installed and running, then create the database:
 *   1. Open pgAdmin or psql
 *   2. CREATE DATABASE campusops;
 *   3. Set your credentials in the .env file at the project root:
 *        DB_URL=jdbc:postgresql://localhost:5432/campusops
 *        DB_USER=postgres
 *        DB_PASS=your_password
 */
public class DatabaseConnector {

    // ── .env file loader ──────────────────────────────────────────────
    private static final Map<String, String> envVars = new HashMap<>();

    static {
        // Load .env file from the project root (if it exists)
        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Skip comments and blank lines
                if (line.isEmpty() || line.startsWith("#")) continue;
                int eqIndex = line.indexOf('=');
                if (eqIndex > 0) {
                    String key   = line.substring(0, eqIndex).trim();
                    String value = line.substring(eqIndex + 1).trim();
                    // Strip surrounding quotes if present
                    if (value.length() >= 2
                            && ((value.startsWith("\"") && value.endsWith("\""))
                            ||  (value.startsWith("'")  && value.endsWith("'")))) {
                        value = value.substring(1, value.length() - 1);
                    }
                    envVars.put(key, value);
                }
            }
            System.out.println("[DB] Loaded credentials from .env file");
        } catch (IOException e) {
            // .env not found — will fall back to system env vars / defaults
            System.out.println("[DB] No .env file found, using system environment variables or defaults");
        }
    }

    // ── Connection settings ───────────────────────────────────────────
    private static final String DB_URL  = getEnvOrDefault("DB_URL",  "jdbc:postgresql://localhost:5432/campusops");
    private static final String DB_USER = getEnvOrDefault("DB_USER", "postgres");
    private static final String DB_PASS = getEnvOrDefault("DB_PASS", "postgres");


    /**
     * Reads a config value: .env file first, then system env var, then fallback.
     */
    private static String getEnvOrDefault(String key, String fallback) {
        // 1. Check .env file
        String value = envVars.get(key);
        if (value != null && !value.isEmpty()) return value;
        // 2. Check system environment variable
        value = System.getenv(key);
        if (value != null && !value.isEmpty()) return value;
        // 3. Use fallback default
        return fallback;
    }

    private Connection connection;

    /** Creates a new DatabaseConnector (does not connect yet — call getConnection()). */
    public DatabaseConnector() {
        this.connection = null;
    }

    /**
     * Opens and returns a connection to the PostgreSQL database.
     * Reuses an existing connection if one is already open.
     *
     * @return an active JDBC Connection
     * @throws SQLException if the connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("[DB] Connected to " + DB_URL);
        }
        return connection;
    }

    /**
     * Closes the database connection if it is open.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Executes a SELECT query and returns the ResultSet.
     *
     * @param sql the SQL SELECT statement
     * @return the ResultSet from the query
     * @throws SQLException if the query fails
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE statement.
     *
     * @param sql the SQL statement
     * @return the number of rows affected
     * @throws SQLException if the statement fails
     */
    public int executeUpdate(String sql) throws SQLException {
        Statement stmt = getConnection().createStatement();
        int rows = stmt.executeUpdate(sql);
        stmt.close();
        return rows;
    }
}
