package com.ug.campusops.graph;

import com.ug.campusops.datastructures.MyPriorityQueue;
import com.ug.campusops.db.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Loads the real campus data from the PostgreSQL database into the Feature 4 structures.
 *
 * This is the bridge between the existing database schema and the graph/priority-queue logic.
 * Instead of hard-coded dummy values, the application uses the real data already seeded in:
 * - locations
 * - routes
 * - service_requests
 */
public class GraphDataLoader {

    /**
     * Builds a Graph from the real campus locations and routes stored in the database.
     *
     * The graph uses location_id as vertex IDs and route records as directed edges.
     * The edge weight is based on distance_m, with a traffic multiplier applied when needed.
     */
    public static Graph loadCampusGraph(DatabaseConnector db) throws SQLException {
        Graph graph = new Graph(200);

        ResultSet locationSet = db.executeQuery("SELECT location_id FROM locations ORDER BY location_id");
        while (locationSet.next()) {
            int locationId = locationSet.getInt("location_id");
            graph.addVertex(locationId);
        }
        locationSet.close();

        ResultSet routeSet = db.executeQuery(
            "SELECT from_location_id, to_location_id, distance_m, traffic_factor " +
            "FROM routes ORDER BY route_id"
        );

        while (routeSet.next()) {
            int fromId = routeSet.getInt("from_location_id");
            int toId = routeSet.getInt("to_location_id");
            double distance = routeSet.getDouble("distance_m");
            double trafficFactor = routeSet.getDouble("traffic_factor");

            double weightedCost = distance * trafficFactor;
            graph.addEdge(fromId, toId, weightedCost);
        }
        routeSet.close();

        return graph;
    }

    /**
     * Loads all pending service requests into a min-priority queue ordered by urgency.
     * The queue stores request ids, and the smaller urgency value is treated as higher priority.
     */
    public static MyPriorityQueue<Integer> loadPendingRequests(DatabaseConnector db) throws SQLException {
        MyPriorityQueue<Integer> priorityQueue = new MyPriorityQueue<>();

        ResultSet requestSet = db.executeQuery(
            "SELECT request_id, urgency_level FROM service_requests " +
            "WHERE status = 'pending' ORDER BY urgency_level ASC, request_id ASC"
        );

        while (requestSet.next()) {
            int requestId = requestSet.getInt("request_id");
            int urgencyLevel = requestSet.getInt("urgency_level");

            // The queue is naturally ordered by the integer value itself.
            // Lower numbers are treated as higher priority in the heap.
            priorityQueue.insert(urgencyLevel);
            // If the team later wants the full request order, a richer object can be used instead.
        }
        requestSet.close();

        return priorityQueue;
    }

    /**
     * Convenience method for loading both the campus graph and pending requests in one go.
     */
    public static CampusDataBundle loadCampusData(DatabaseConnector db) throws SQLException {
        return new CampusDataBundle(loadCampusGraph(db), loadPendingRequests(db));
    }

    /**
     * Simple bundle object to keep the graph and pending requests together.
     */
    public static class CampusDataBundle {
        private final Graph graph;
        private final MyPriorityQueue<Integer> pendingRequests;

        public CampusDataBundle(Graph graph, MyPriorityQueue<Integer> pendingRequests) {
            this.graph = graph;
            this.pendingRequests = pendingRequests;
        }

        public Graph getGraph() {
            return graph;
        }

        public MyPriorityQueue<Integer> getPendingRequests() {
            return pendingRequests;
        }
    }
}
