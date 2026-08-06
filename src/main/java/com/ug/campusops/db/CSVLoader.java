package com.ug.campusops.db;

import com.ug.campusops.model.Location;
import com.ug.campusops.model.Resource;
import com.ug.campusops.model.Route;
import com.ug.campusops.model.ServiceRequest;

/**
 * Reads CSV data files and inserts them into the PostgreSQL database.
 * This is the "seed" process that populates the database with our UG Legon campus data.
 *
 * Feature 1 — Data Loader
 *
 * CSV files are located in the data/ directory:
 *   - data/locations.csv  (60 records)
 *   - data/routes.csv     (110 records)
 *   - data/resources.csv  (35 records)
 *   - data/requests.csv   (310 records)
 */
public class CSVLoader {

    private DatabaseConnector dbConnector;

    /**
     * Creates a CSVLoader that uses the given database connector.
     *
     * @param dbConnector the database connector to use for inserts
     */
    public CSVLoader(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    /**
     * Reads locations.csv and inserts all rows into the locations table.
     *
     * @param filePath path to locations.csv
     * @return the number of rows inserted
     */
    public int loadLocations(String filePath) {
        // TODO: Feature 1 team — implement this
        //   1. Open the file with BufferedReader
        //   2. Skip the header line
        //   3. For each line: split by comma, create INSERT INTO locations VALUES(...)
        //   4. Execute each insert via dbConnector.executeUpdate()
        //   5. Count and return successful inserts
        throw new UnsupportedOperationException("CSVLoader.loadLocations() not yet implemented");
    }

    /**
     * Reads routes.csv and inserts all rows into the routes table.
     *
     * @param filePath path to routes.csv
     * @return the number of rows inserted
     */
    public int loadRoutes(String filePath) {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("CSVLoader.loadRoutes() not yet implemented");
    }

    /**
     * Reads resources.csv and inserts all rows into the resources table.
     *
     * @param filePath path to resources.csv
     * @return the number of rows inserted
     */
    public int loadResources(String filePath) {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("CSVLoader.loadResources() not yet implemented");
    }

    /**
     * Reads requests.csv and inserts all rows into the service_requests table.
     *
     * @param filePath path to requests.csv
     * @return the number of rows inserted
     */
    public int loadRequests(String filePath) {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("CSVLoader.loadRequests() not yet implemented");
    }

    /**
     * Loads ALL CSV files at once (convenience method).
     * Calls loadLocations, loadRoutes, loadResources, loadRequests in order.
     *
     * @param dataDir path to the data/ directory
     */
    public void loadAll(String dataDir) {
        // TODO: Feature 1 team — implement this
        throw new UnsupportedOperationException("CSVLoader.loadAll() not yet implemented");
    }
}
