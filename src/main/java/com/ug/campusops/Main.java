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

        // TODO: All teams — wire up the startup sequence here once features are ready
        
        // //Step 1: Connect to database
        // DatabaseConnector db = new DatabaseConnector();
        
        // //Step 2: Set up schema and load data
        // SchemaSetup schema = new SchemaSetup(db);
        // schema.createTables();
        // schema.seedFromCSV("data"); // uncomment on first run
        
        // //Step 3: Build graph from database
        // Graph campusGraph = new Graph(60);
        // // load locations and routes from DB, add to graph
        
        // //Step 4: Build priority queue from pending requests
        // // load pending requests from DB, add to priority queue
        
       // Step 5: Launch console menu
        ConsoleMenu menu = new ConsoleMenu();
        menu.start();

        // System.out.println("[INFO] Main class is ready. Features are not yet wired up.");
        // System.out.println("[INFO] Each feature team should implement their stubs first,");
        // System.out.println("[INFO] then Feature 9 team connects everything here.");
    }
}
