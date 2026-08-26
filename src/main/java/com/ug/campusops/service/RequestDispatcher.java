package com.ug.campusops.service;

import com.ug.campusops.db.DatabaseConnector;
import com.ug.campusops.graph.Dijkstra;
import com.ug.campusops.graph.Graph;
import com.ug.campusops.model.Resource;
import com.ug.campusops.model.ServiceRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Takes a new service request, saves it, and decides which resource (repair
 * person)
 * should be sent to handle it.
 *
 * Feature 8 — Service Layer
 *
 * Step-by-step (from Planning team pseudocode):
 * 1. A new problem comes in (location, type, urgency)
 * 2. Save it to the database as "pending"
 * 3. Add it to the priority queue, ordered by urgency
 * 4. Find the nearest available repair person/vehicle to that location
 * 5. Mark the problem as "assigned" to that person, save to database
 */
public class RequestDispatcher {

    private DatabaseConnector dbConnector;
    private Graph campusGraph;
    private SchedulingEngine schedulingEngine;

    /**
     * Creates a RequestDispatcher with database and graph access.
     *
     * @param dbConnector database connector for persisting requests
     * @param campusGraph the campus graph for distance calculations
     */
    public RequestDispatcher(DatabaseConnector dbConnector, Graph campusGraph) {
        this(dbConnector, campusGraph, new SchedulingEngine());
    }

    public RequestDispatcher(DatabaseConnector dbConnector, Graph campusGraph,
            SchedulingEngine schedulingEngine) {
        this.dbConnector = dbConnector;
        this.campusGraph = campusGraph;
        this.schedulingEngine = schedulingEngine;
    }

    /**
     * Submits a new service request: saves to database and adds to priority queue.
     *
     * @param request the new service request to submit
     */
    public void submitRequest(ServiceRequest request) {
        if (request == null)
            throw new IllegalArgumentException("request must not be null");
        request.setStatus("pending");
        request.setAssignedResourceId(0);
        String deadline = request.getDeadline() == null || request.getDeadline().isBlank()
                ? "NULL"
                : "'" + sql(request.getDeadline()) + "'";
        String submitted = request.getTimeSubmitted() == null || request.getTimeSubmitted().isBlank()
                ? "NOW()"
                : "'" + sql(request.getTimeSubmitted()) + "'";
        String destination = request.getDestinationLocationId() <= 0
                ? "NULL"
                : Integer.toString(request.getDestinationLocationId());
        update("INSERT INTO service_requests (source_location_id, destination_location_id, category, "
                + "urgency_level, time_submitted, deadline, status, assigned_resource_id) VALUES ("
                + request.getSourceLocationId() + ", " + destination + ", '"
                + sql(request.getCategory()) + "', " + request.getUrgencyLevel() + ", " + submitted
                + ", " + deadline + ", 'pending', NULL)");
        schedulingEngine.addToQueue(request);
    }

    /**
     * Assigns the nearest available resource to a specific request.
     *
     * @param requestId the ID of the request to assign
     * @return the ID of the assigned resource, or -1 if none available
     */
    public int assignResource(int requestId) {
        try (ResultSet result = dbConnector.executeQuery(
                "SELECT source_location_id FROM service_requests WHERE request_id = " + requestId)) {
            if (!result.next())
                return -1;
            Resource resource = findNearestResource(result.getInt("source_location_id"));
            if (resource == null)
                return -1;
            update("UPDATE resources SET availability_status = 'busy' WHERE resource_id = "
                    + resource.getResourceId());
            update("UPDATE service_requests SET assigned_resource_id = " + resource.getResourceId()
                    + ", status = 'assigned' WHERE request_id = " + requestId);
            return resource.getResourceId();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not assign resource", exception);
        }
    }

    /**
     * Finds the nearest available resource to a given location.
     * Uses Dijkstra's algorithm to calculate distances.
     *
     * @param locationId the location that needs a resource
     * @return the nearest available Resource, or null if none available
     */
    public Resource findNearestResource(int locationId) {
        if (campusGraph == null)
            throw new IllegalStateException("campus graph is required");
        Dijkstra.ShortestPathResult paths = Dijkstra.allShortestPaths(campusGraph, locationId);
        Resource nearest = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        try (ResultSet result = dbConnector.executeQuery(
                "SELECT resource_id, type, home_location_id, capacity, availability_status FROM resources "
                        + "WHERE availability_status = 'available' AND capacity > 0")) {
            while (result.next()) {
                int homeLocation = result.getInt("home_location_id");
                if (homeLocation < 0 || homeLocation >= paths.distances.length)
                    continue;
                double distance = paths.distances[homeLocation];
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = new Resource(result.getInt("resource_id"), result.getString("type"),
                            homeLocation, result.getInt("capacity"), result.getString("availability_status"));
                }
            }
            return nearest;
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find available resources", exception);
        }
    }

    private void update(String statement) {
        try {
            dbConnector.executeUpdate(statement);
        } catch (SQLException exception) {
            throw new IllegalStateException("Database update failed", exception);
        }
    }

    private String sql(String value) {
        return value.replace("'", "''");
    }
}
