package com.ug.campusops.algorithms;

import com.ug.campusops.db.DatabaseConnector;
import com.ug.campusops.graph.Graph;
import com.ug.campusops.model.Resource;
import com.ug.campusops.model.ServiceRequest;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Greedy, run entirely against the REAL PostgreSQL database
 * (campusops). No hardcoded requests/resources and no FakeGraph — every
 * value asserted against is either read from the DB directly or computed
 * independently from the same DB rows the algorithm itself sees.
 *
 *   mvn test -Dtest=GreedyTest
 *
 * PREREQUISITES:
 *   - Postgres running locally, .env configured (same setup ResetDB uses)
 *   - Database already seeded, e.g. via: java -cp ... com.ug.campusops.ResetDB
 *
 * If the DB isn't reachable, every test in this class is SKIPPED (not
 * failed) via Assumptions.
 *
 * NOTE on Graph: Feature 4's Graph.getWeight() is still a stub
 * (UnsupportedOperationException). Real route distances are pulled directly
 * from the `routes` table via the DbRouteGraph adapter below so this test
 * isn't blocked on Feature 4. Once Graph is implemented for real, swap
 * DbRouteGraph for `new Graph(...)` populated via addEdge() — nothing else
 * in this test (or in Greedy.nearestResourceAssignment) needs to change.
 */
class GreedyTest {

    private static DatabaseConnector db;
    private static boolean dbAvailable = false;

    @BeforeAll
    static void connect() {
        db = new DatabaseConnector();
        try {
            db.getConnection();
            dbAvailable = true;
        } catch (SQLException e) {
            System.out.println("[GreedyTest] DB not reachable, skipping: " + e.getMessage());
            dbAvailable = false;
        }
    }

    @AfterAll
    static void disconnect() {
        if (db != null) db.closeConnection();
    }

    @BeforeEach
    void requireDb() {
        Assumptions.assumeTrue(dbAvailable, "Skipping: database not reachable");
    }

    // ── Data loading helpers (read-only, no schema changes) ────────────

    private List<ServiceRequest> loadPendingRequests(int limit) throws SQLException {
        String sql = "SELECT request_id, source_location_id, destination_location_id, "
                + "category, urgency_level, time_submitted, deadline, status, assigned_resource_id "
                + "FROM service_requests WHERE status = 'pending' ORDER BY request_id LIMIT ?";
        List<ServiceRequest> out = new ArrayList<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int destId = rs.getObject("destination_location_id") == null
                            ? 0 : rs.getInt("destination_location_id");
                    int assignedId = rs.getObject("assigned_resource_id") == null
                            ? 0 : rs.getInt("assigned_resource_id");
                    out.add(new ServiceRequest(
                            rs.getInt("request_id"),
                            rs.getInt("source_location_id"),
                            destId,
                            rs.getString("category"),
                            rs.getInt("urgency_level"),
                            rs.getTimestamp("time_submitted").toString(),
                            rs.getTimestamp("deadline") == null ? null : rs.getTimestamp("deadline").toString(),
                            rs.getString("status"),
                            assignedId
                    ));
                }
            }
        }
        return out;
    }

    private List<Resource> loadResourcesByStatus(String status, int limit) throws SQLException {
        String sql = "SELECT resource_id, type, home_location_id, capacity, availability_status "
                + "FROM resources WHERE availability_status = ? ORDER BY resource_id LIMIT ?";
        List<Resource> out = new ArrayList<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Resource(
                            rs.getInt("resource_id"),
                            rs.getString("type"),
                            rs.getInt("home_location_id"),
                            rs.getInt("capacity"),
                            rs.getString("availability_status")
                    ));
                }
            }
        }
        return out;
    }

    /**
     * Graph adapter backed directly by the real `routes` table, since
     * Graph.getWeight() (Feature 4) is not implemented yet. Loads every
     * route row into an in-memory lookup once, then answers getWeight()
     * from that. Temporary — delete once Graph is finished.
     */
    private static class DbRouteGraph extends Graph {
        private final Map<Long, Double> weights = new HashMap<>();

        DbRouteGraph(Connection conn) throws SQLException {
            super(1); // vertex count unused by our lookup
            String sql = "SELECT from_location_id, to_location_id, distance_m FROM routes";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int from = rs.getInt("from_location_id");
                    int to = rs.getInt("to_location_id");
                    double dist = rs.getInt("distance_m");
                    weights.put(key(from, to), dist);
                    weights.put(key(to, from), dist); // treat routes as bidirectional
                }
            }
        }

        @Override
        public double getWeight(int fromId, int toId) {
            if (fromId == toId) return 0;
            return weights.getOrDefault(key(fromId, toId), -1.0);
        }

        private long key(int fromId, int toId) {
            return ((long) fromId << 32) | (toId & 0xffffffffL);
        }
    }

    // ── Tests ────────────────────────────────────────────────────────

    /**
     * Replaces testGreedyCounterExampleRunsAndShowsGap. This method takes no
     * data at all (it's a fixed worked example baked into Greedy.java to
     * document why greedy isn't optimal), so there's nothing to source from
     * the DB — kept here so all Greedy evidence lives in one file.
     */
    @Test
    void testGreedyCounterExampleShowsGapVsOptimal() {
        String result = Greedy.greedyCounterExample();
        assertNotNull(result);
        assertTrue(result.contains("600"), "should report greedy total of 600");
        assertTrue(result.contains("230"), "should report optimal total of 230");
    }

    /**
     * Replaces testNearestResourceAssignmentPicksClosest. Instead of two
     * hardcoded distances, this pulls a real pending request and every real
     * available resource reachable from it, independently computes which
     * resource is actually closest using the same live route data the
     * algorithm reads, then asserts Greedy picked that same resource.
     */
    @Test
    void testNearestResourceAssignmentPicksClosestRealResource() throws SQLException {
        List<ServiceRequest> requests = loadPendingRequests(1);
        Assumptions.assumeTrue(!requests.isEmpty(), "No pending requests in DB to test with");

        DbRouteGraph graph = new DbRouteGraph(db.getConnection());
        ServiceRequest request = requests.get(0);

        List<Resource> candidates = loadResourcesByStatus("available", 20);
        List<Resource> reachable = new ArrayList<>();
        for (Resource r : candidates) {
            if (graph.getWeight(request.getSourceLocationId(), r.getHomeLocationId()) >= 0) {
                reachable.add(r);
            }
        }
        Assumptions.assumeTrue(reachable.size() >= 2,
                "Need at least 2 reachable available resources in DB to test with");

        // Independently determine the closest resource, using the exact
        // same first-strictly-smaller-wins tie-break Greedy itself uses.
        Resource expectedClosest = reachable.get(0);
        double bestDist = graph.getWeight(request.getSourceLocationId(), expectedClosest.getHomeLocationId());
        for (Resource r : reachable) {
            double d = graph.getWeight(request.getSourceLocationId(), r.getHomeLocationId());
            if (d < bestDist) {
                bestDist = d;
                expectedClosest = r;
            }
        }

        ServiceRequest[] reqArr = { request };
        Resource[] resArr = reachable.toArray(new Resource[0]);

        int[] assignments = Greedy.nearestResourceAssignment(reqArr, resArr, graph);

        assertEquals(expectedClosest.getResourceId(), assignments[0],
                "should assign the resource closest by real route distance");
        assertEquals("busy", expectedClosest.getAvailabilityStatus(),
                "assigned real resource should be marked busy");
    }

    /**
     * Replaces testNearestResourceAssignmentReturnsMinusOneWhenNoneAvailable.
     * The original version filtered on availability_status = 'busy', but the
     * seeded data has no non-'available' resources (all 35 rows are
     * 'available'), so that condition can never occur for real. Instead this
     * tests the same -1 outcome via a condition the real data CAN produce:
     * a request whose source location has no route to any available
     * resource's home location at all.
     */
    @Test
    void testNearestResourceAssignmentReturnsMinusOneWhenUnreachable() throws SQLException {
        List<ServiceRequest> requests = loadPendingRequests(200);
        List<Resource> resources = loadResourcesByStatus("available", 100);

        Assumptions.assumeTrue(!requests.isEmpty(), "No pending requests in DB to test with");
        Assumptions.assumeTrue(!resources.isEmpty(), "No available resources in DB to test with");

        DbRouteGraph graph = new DbRouteGraph(db.getConnection());

        ServiceRequest unreachable = null;
        for (ServiceRequest r : requests) {
            boolean anyReachable = false;
            for (Resource res : resources) {
                if (graph.getWeight(r.getSourceLocationId(), res.getHomeLocationId()) >= 0) {
                    anyReachable = true;
                    break;
                }
            }
            if (!anyReachable) {
                unreachable = r;
                break;
            }
        }

        Assumptions.assumeTrue(unreachable != null,
                "No pending request in DB is unreachable from every available resource "
                        + "(every request has at least one valid route) — nothing to test here");

        ServiceRequest[] reqArr = { unreachable };
        Resource[] resArr = resources.toArray(new Resource[0]);

        int[] assignments = Greedy.nearestResourceAssignment(reqArr, resArr, graph);

        assertEquals(-1, assignments[0],
                "request has no route to any available resource, so assignment should be -1");
    }

    /**
     * Replaces testNearestResourceAssignmentDoesNotDoubleBookSameResource.
     * Generalized to real data volume: many real pending requests competing
     * over a deliberately small pool of real available resources. Confirms
     * no resource id is ever handed out twice within the same batch.
     */
    @Test
    void testNearestResourceAssignmentDoesNotDoubleBookSameResource() throws SQLException {
        List<ServiceRequest> requests = loadPendingRequests(30);
        List<Resource> resources = loadResourcesByStatus("available", 5); // deliberately scarce

        Assumptions.assumeTrue(!requests.isEmpty(), "No pending requests in DB to test with");
        Assumptions.assumeTrue(!resources.isEmpty(), "No available resources in DB to test with");

        DbRouteGraph graph = new DbRouteGraph(db.getConnection());

        ServiceRequest[] reqArr = requests.toArray(new ServiceRequest[0]);
        Resource[] resArr = resources.toArray(new Resource[0]);

        int[] assignments = Greedy.nearestResourceAssignment(reqArr, resArr, graph);

        Map<Integer, Integer> usageCount = new HashMap<>();
        for (int resourceId : assignments) {
            if (resourceId == -1) continue;
            usageCount.merge(resourceId, 1, Integer::sum);
        }
        for (Map.Entry<Integer, Integer> entry : usageCount.entrySet()) {
            assertEquals(1, entry.getValue(),
                    "resource id " + entry.getKey() + " was assigned more than once in the same batch");
        }

        System.out.println("[GreedyTest] " + usageCount.size() + " distinct real resources used, "
                + "no double-booking across " + reqArr.length + " real requests.");
    }
}