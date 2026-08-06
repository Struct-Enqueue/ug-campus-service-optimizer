package com.ug.campusops.service;

import com.ug.campusops.db.DatabaseConnector;
import com.ug.campusops.graph.Dijkstra;
import com.ug.campusops.graph.Graph;
import com.ug.campusops.model.Resource;
import com.ug.campusops.model.ServiceRequest;

/**
 * Takes a new service request, saves it, and decides which resource (repair person)
 * should be sent to handle it.
 *
 * Feature 8 — Service Layer
 *
 * Step-by-step (from Planning team pseudocode):
 *   1. A new problem comes in (location, type, urgency)
 *   2. Save it to the database as "pending"
 *   3. Add it to the priority queue, ordered by urgency
 *   4. Find the nearest available repair person/vehicle to that location
 *   5. Mark the problem as "assigned" to that person, save to database
 */
public class RequestDispatcher {

    private DatabaseConnector dbConnector;
    private Graph campusGraph;

    /**
     * Creates a RequestDispatcher with database and graph access.
     *
     * @param dbConnector database connector for persisting requests
     * @param campusGraph the campus graph for distance calculations
     */
    public RequestDispatcher(DatabaseConnector dbConnector, Graph campusGraph) {
        this.dbConnector = dbConnector;
        this.campusGraph = campusGraph;
    }

    /**
     * Submits a new service request: saves to database and adds to priority queue.
     *
     * @param request the new service request to submit
     */
    public void submitRequest(ServiceRequest request) {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("RequestDispatcher.submitRequest() not yet implemented");
    }

    /**
     * Assigns the nearest available resource to a specific request.
     *
     * @param requestId the ID of the request to assign
     * @return the ID of the assigned resource, or -1 if none available
     */
    public int assignResource(int requestId) {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("RequestDispatcher.assignResource() not yet implemented");
    }

    /**
     * Finds the nearest available resource to a given location.
     * Uses Dijkstra's algorithm to calculate distances.
     *
     * @param locationId the location that needs a resource
     * @return the nearest available Resource, or null if none available
     */
    public Resource findNearestResource(int locationId) {
        // TODO: Feature 8 team — implement this
        throw new UnsupportedOperationException("RequestDispatcher.findNearestResource() not yet implemented");
    }
}
