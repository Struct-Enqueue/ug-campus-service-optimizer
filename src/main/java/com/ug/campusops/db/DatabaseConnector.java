package com.ug.campusops.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the PostgreSQL database connection for the campus operations system.
 * All database access goes through this class.
 *
 * Feature 1 — Data Loader
 *
 * SETUP: Ensure PostgreSQL is installed and running, then create the database:
 *   1. Open pgAdmin or psql
 *   2. CREATE DATABASE campusops;
 *   3. Update the credentials below if your PostgreSQL password is different
 */
public class DatabaseConnector {

    // ── Connection settings (update these for your machine) ────────────
    private static final String DB_URL  = "jdbc:postgresql://localhost:5432/campusops";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "postgres"; // TODO: Change to your PostgreSQL password

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
        // TODO: Feature 1 team — implement this
        //   Use DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)
        //   Check if connection is null or closed before creating a new one
        throw new UnsupportedOperationException("DatabaseConnector.getConnection() not yet implemented");
    }

    /**
     * Closes the database connection if it is open.
     */
    public void closeConnection() {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("DatabaseConnector.closeConnection() not yet implemented");
    }

    /**
     * Executes a SELECT query and returns the ResultSet.
     *
     * @param sql the SQL SELECT statement
     * @return the ResultSet from the query
     * @throws SQLException if the query fails
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("DatabaseConnector.executeQuery() not yet implemented");
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE statement.
     *
     * @param sql the SQL statement
     * @return the number of rows affected
     * @throws SQLException if the statement fails
     */
    public int executeUpdate(String sql) throws SQLException {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("DatabaseConnector.executeUpdate() not yet implemented");
    }
}
