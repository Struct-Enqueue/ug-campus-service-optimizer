package com.ug.campusops.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
     * CSV columns: locationId, name, area, type, latitude, longitude
     *
     * @param filePath path to locations.csv
     * @return the number of rows inserted
     */
    public int loadLocations(String filePath) {
        int count = 0;
        String sql = "INSERT INTO locations (location_id, name, area, type, latitude, longitude) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            br.readLine();

            PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(sql);
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] fields = line.split(",", -1);
                // locationId, name, area, type, latitude, longitude
                pstmt.setInt(1, Integer.parseInt(fields[0].trim()));
                pstmt.setString(2, fields[1].trim());
                pstmt.setString(3, fields[2].trim());
                pstmt.setString(4, fields[3].trim());
                pstmt.setDouble(5, Double.parseDouble(fields[4].trim()));
                pstmt.setDouble(6, Double.parseDouble(fields[5].trim()));

                pstmt.executeUpdate();
                count++;
            }
            pstmt.close();
            System.out.println("[CSV] Loaded " + count + " locations from " + filePath);

        } catch (IOException e) {
            System.err.println("[CSV] Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[CSV] Database error loading locations: " + e.getMessage());
        }
        return count;
    }

    /**
     * Reads routes.csv and inserts all rows into the routes table.
     * CSV columns: routeId, fromLocationId, toLocationId, distanceM, avgTimeMin, trafficFactor
     *
     * @param filePath path to routes.csv
     * @return the number of rows inserted
     */
    public int loadRoutes(String filePath) {
        int count = 0;
        String sql = "INSERT INTO routes (route_id, from_location_id, to_location_id, distance_m, avg_time_min, traffic_factor) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(sql);
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] fields = line.split(",", -1);
                // routeId, fromLocationId, toLocationId, distanceM, avgTimeMin, trafficFactor
                pstmt.setInt(1, Integer.parseInt(fields[0].trim()));
                pstmt.setInt(2, Integer.parseInt(fields[1].trim()));
                pstmt.setInt(3, Integer.parseInt(fields[2].trim()));
                pstmt.setInt(4, Integer.parseInt(fields[3].trim()));
                pstmt.setInt(5, Integer.parseInt(fields[4].trim()));
                pstmt.setDouble(6, Double.parseDouble(fields[5].trim()));

                pstmt.executeUpdate();
                count++;
            }
            pstmt.close();
            System.out.println("[CSV] Loaded " + count + " routes from " + filePath);

        } catch (IOException e) {
            System.err.println("[CSV] Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[CSV] Database error loading routes: " + e.getMessage());
        }
        return count;
    }

    /**
     * Reads resources.csv and inserts all rows into the resources table.
     * CSV columns: resourceId, type, homeLocationId, capacity, availabilityStatus
     *
     * @param filePath path to resources.csv
     * @return the number of rows inserted
     */
    public int loadResources(String filePath) {
        int count = 0;
        String sql = "INSERT INTO resources (resource_id, type, home_location_id, capacity, availability_status) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(sql);
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] fields = line.split(",", -1);
                // resourceId, type, homeLocationId, capacity, availabilityStatus
                pstmt.setInt(1, Integer.parseInt(fields[0].trim()));
                pstmt.setString(2, fields[1].trim());
                pstmt.setInt(3, Integer.parseInt(fields[2].trim()));
                pstmt.setInt(4, Integer.parseInt(fields[3].trim()));
                pstmt.setString(5, fields[4].trim());

                pstmt.executeUpdate();
                count++;
            }
            pstmt.close();
            System.out.println("[CSV] Loaded " + count + " resources from " + filePath);

        } catch (IOException e) {
            System.err.println("[CSV] Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[CSV] Database error loading resources: " + e.getMessage());
        }
        return count;
    }

    /**
     * Reads requests.csv and inserts all rows into the service_requests table.
     * CSV columns: requestId, sourceLocationId, destinationLocationId, category,
     *              urgencyLevel, timeSubmitted, deadline, status, assignedResourceId
     *
     * @param filePath path to requests.csv
     * @return the number of rows inserted
     */
    public int loadRequests(String filePath) {
        int count = 0;
        String sql = "INSERT INTO service_requests (request_id, source_location_id, destination_location_id, "
                   + "category, urgency_level, time_submitted, deadline, status, assigned_resource_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            PreparedStatement pstmt = dbConnector.getConnection().prepareStatement(sql);
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] fields = line.split(",", -1);
                // requestId, sourceLocationId, destinationLocationId, category,
                // urgencyLevel, timeSubmitted, deadline, status, assignedResourceId
                pstmt.setInt(1, Integer.parseInt(fields[0].trim()));
                pstmt.setInt(2, Integer.parseInt(fields[1].trim()));

                // destinationLocationId can be same as source or different
                int destId = Integer.parseInt(fields[2].trim());
                if (destId == 0) {
                    pstmt.setNull(3, java.sql.Types.INTEGER);
                } else {
                    pstmt.setInt(3, destId);
                }

                pstmt.setString(4, fields[3].trim());
                pstmt.setInt(5, Integer.parseInt(fields[4].trim()));
                pstmt.setTimestamp(6, Timestamp.valueOf(fields[5].trim().replace("T", " ")));
                pstmt.setTimestamp(7, Timestamp.valueOf(fields[6].trim().replace("T", " ")));
                pstmt.setString(8, fields[7].trim());

                // assignedResourceId: 0 means not assigned
                int resourceId = Integer.parseInt(fields[8].trim());
                if (resourceId == 0) {
                    pstmt.setNull(9, java.sql.Types.INTEGER);
                } else {
                    pstmt.setInt(9, resourceId);
                }

                pstmt.executeUpdate();
                count++;
            }
            pstmt.close();
            System.out.println("[CSV] Loaded " + count + " service requests from " + filePath);

        } catch (IOException e) {
            System.err.println("[CSV] Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[CSV] Database error loading requests: " + e.getMessage());
        }
        return count;
    }

    /**
     * Loads ALL CSV files at once (convenience method).
     * Calls loadLocations, loadRoutes, loadResources, loadRequests in order.
     * Order matters because routes and resources reference locations,
     * and requests reference both locations and resources.
     *
     * @param dataDir path to the data/ directory
     */
    public void loadAll(String dataDir) {
        // Ensure trailing separator
        if (!dataDir.endsWith("/") && !dataDir.endsWith("\\")) {
            dataDir += "/";
        }

        System.out.println("============================================");
        System.out.println("  Loading all CSV data into campusops DB");
        System.out.println("============================================");

        int locations = loadLocations(dataDir + "locations.csv");
        int routes    = loadRoutes(dataDir + "routes.csv");
        int resources = loadResources(dataDir + "resources.csv");
        int requests  = loadRequests(dataDir + "requests.csv");

        System.out.println("============================================");
        System.out.println("  Load complete!");
        System.out.println("  Locations:        " + locations);
        System.out.println("  Routes:           " + routes);
        System.out.println("  Resources:        " + resources);
        System.out.println("  Service Requests: " + requests);
        System.out.println("============================================");
    }
}
