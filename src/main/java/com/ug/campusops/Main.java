package com.ug.campusops;

import com.ug.campusops.db.DatabaseConnector;
import com.ug.campusops.db.SchemaSetup;
import com.ug.campusops.ui.ConsoleMenu;

/**
 * Entry point for the Campus Smart Service Operations Optimizer.
 *
 * Startup sequence:
 *   1. Connect to PostgreSQL database
 *   2. Create tables if they don't exist
 *   3. Load CSV data if database is empty
 *   4. Build graph from locations and routes
 *   5. Build priority queue from pending requests
 *   6. Launch the console menu
 *
 * To run:  mvn compile exec:java -Dexec.mainClass="com.ug.campusops.Main"
 * Or:      java -cp target/classes com.ug.campusops.Main
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  UG Campus Smart Service Operations Optimizer");
        System.out.println("  University of Ghana, Legon");
        System.out.println("  DCIT 204/308 DSA Project");
        System.out.println("==============================================");
        System.out.println();

        // ── Feature 1: Database connection & data loading ─────────────
        DatabaseConnector db = new DatabaseConnector();

        try {
            // Set up schema (creates tables if they don't exist)
            SchemaSetup schema = new SchemaSetup(db);

            // Check if data is already loaded
            java.sql.ResultSet rs = db.executeQuery("SELECT COUNT(*) FROM locations");
            rs.next();
            int existingCount = rs.getInt(1);
            rs.close();

            if (existingCount == 0) {
                // First run — load CSV data
                System.out.println("[INFO] Database is empty. Loading CSV data...");
                schema.seedFromCSV("data");
            } else {
                System.out.println("[INFO] Database already has " + existingCount + " locations. Skipping CSV load.");
            }

            // ── Verification: print row counts ────────────────────────
            System.out.println();
            System.out.println("==============================================");
            System.out.println("  VERIFICATION — Table Row Counts");
            System.out.println("==============================================");

            String[] tables = {"locations", "routes", "resources", "service_requests"};
             int[] expected  = {60, 120, 35, 310};
            for (int i = 0; i < tables.length; i++) {
                rs = db.executeQuery("SELECT COUNT(*) FROM " + tables[i]);
                rs.next();
                int count = rs.getInt(1);
                rs.close();
                String status = (count == expected[i]) ? "✓ PASS" : "✗ FAIL (expected " + expected[i] + ")";
                System.out.printf("  %-20s : %4d  %s%n", tables[i], count, status);
            }

            // Quick sanity check
            rs = db.executeQuery("SELECT name FROM locations WHERE location_id = 1");
            if (rs.next()) {
                System.out.println();
                System.out.println("  Sanity check: location_id=1 → " + rs.getString("name"));
            }
            rs.close();

            System.out.println("==============================================");

        } catch (java.sql.SQLException e) {
            System.err.println("[ERROR] Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        // TODO: Steps 3-5 — other feature teams wire up graph, priority queue, and console menu
        // Step 3: Build graph from database
        // Graph campusGraph = new Graph(60);
        // // load locations and routes from DB, add to graph
        //
        // Step 4: Build priority queue from pending requests
        // // load pending requests from DB, add to priority queue
        //
        // Step 5: Launch console menu
        // ConsoleMenu menu = new ConsoleMenu();
        // menu.start();
    }
}
