package com.ug.campusops.algorithms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ug.campusops.db.DatabaseConnector;
import com.ug.campusops.model.ServiceRequest;

/**
 * Tests for DynamicProgramming, run entirely against the REAL PostgreSQL
 * database (campusops). No hardcoded ServiceRequest arrays — every cost and
 * value used comes from real rows in service_requests.
 *
 *   mvn test -Dtest=DynamicProgrammingTest
 *
 * PREREQUISITES:
 *   - Postgres running locally, .env configured (same setup ResetDB uses)
 *   - Database already seeded, e.g. via: java -cp ... com.ug.campusops.ResetDB
 *
 * If the DB isn't reachable, every test in this class is SKIPPED (not
 * failed) via Assumptions.
 *
 * NOTE: unlike Greedy, requestKnapsack()/optimalRouteBudget() have no
 * dependency on Graph, so these tests need no workaround — they query
 * service_requests directly.
 */
class DynamicProgrammingTest {

    private static DatabaseConnector db;
    private static boolean dbAvailable = false;

    @BeforeAll
    static void connect() {
        db = new DatabaseConnector();
        try {
            db.getConnection();
            dbAvailable = true;
        } catch (SQLException e) {
            System.out.println("[DynamicProgrammingTest] DB not reachable, skipping: " + e.getMessage());
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

    /** Simple 2^n brute force used only to validate the DP result in tests. */
    private static int bruteForceKnapsack(int[] cost, int[] value, int budget) {
        int n = cost.length;
        int best = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            int c = 0, v = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    c += cost[i];
                    v += value[i];
                }
            }
            if (c <= budget && v > best) best = v;
        }
        return best;
    }

    /**
     * Replaces testOptimalRouteBudgetMatchesBruteForce and
     * testRequestKnapsackWithServiceRequests together: cost/value pairs are
     * derived from real pending requests (via the same estimateCost()/
     * getUrgencyLevel() the algorithm itself uses), then requestKnapsack's
     * result is cross-checked against a brute-force search over those exact
     * real values. Limited to 15 real requests to keep 2^n brute force fast.
     */
    @Test
    void testRequestKnapsackMatchesBruteForceOnRealData() throws SQLException {
        List<ServiceRequest> requests = loadPendingRequests(15);
        Assumptions.assumeTrue(!requests.isEmpty(), "No pending requests in DB to test with");

        ServiceRequest[] reqArr = requests.toArray(new ServiceRequest[0]);
        int budgetMinutes = 240;

        int[] cost = new int[reqArr.length];
        int[] value = new int[reqArr.length];
        for (int i = 0; i < reqArr.length; i++) {
            cost[i] = DynamicProgramming.estimateCost(reqArr[i]);
            value[i] = reqArr[i].getUrgencyLevel();
        }

        DynamicProgramming.KnapsackResult result = DynamicProgramming.requestKnapsack(reqArr, budgetMinutes);
        int expected = bruteForceKnapsack(cost, value, budgetMinutes);

        assertEquals(expected, result.maxValue,
                "requestKnapsack result must match brute force over the same real cost/value data");

        int totalCost = 0, totalValue = 0;
        for (int i = 0; i < reqArr.length; i++) {
            if (result.selectedItems[i]) {
                totalCost += cost[i];
                totalValue += value[i];
            }
        }
        assertTrue(totalCost <= budgetMinutes, "selection must not exceed budget");
        assertEquals(result.maxValue, totalValue, "reported maxValue must match reconstructed selection");
    }

    /** Replaces testZeroBudgetSelectsNothing, using real pending requests. */
    @Test
    void testZeroBudgetSelectsNothing() throws SQLException {
        List<ServiceRequest> requests = loadPendingRequests(20);
        Assumptions.assumeTrue(!requests.isEmpty(), "No pending requests in DB to test with");

        ServiceRequest[] reqArr = requests.toArray(new ServiceRequest[0]);
        DynamicProgramming.KnapsackResult result = DynamicProgramming.requestKnapsack(reqArr, 0);

        assertEquals(0, result.maxValue);
        for (boolean selected : result.selectedItems) {
            assertFalse(selected);
        }
    }

    /**
     * Replaces testSingleItemFitsExactly. Uses exactly one real pending
     * request, with the budget set to that request's own estimated cost —
     * so it fits with zero slack, using real numbers instead of a fake
     * cost/value pair.
     */
    @Test
    void testSingleRealRequestFitsExactly() throws SQLException {
        List<ServiceRequest> requests = loadPendingRequests(1);
        Assumptions.assumeTrue(!requests.isEmpty(), "No pending requests in DB to test with");

        ServiceRequest[] reqArr = { requests.get(0) };
        int exactCost = DynamicProgramming.estimateCost(reqArr[0]);

        DynamicProgramming.KnapsackResult result = DynamicProgramming.requestKnapsack(reqArr, exactCost);

        assertEquals(reqArr[0].getUrgencyLevel(), result.maxValue);
        assertTrue(result.selectedItems[0]);
    }

    /**
     * Replaces the budget-respecting assertions of the original tests at
     * larger, more realistic data volume: 100 real requests against a tight
     * 4-hour shift.
     */
    @Test
    void testRequestKnapsackRespectsBudgetAtScale() throws SQLException {
        List<ServiceRequest> requests = loadPendingRequests(100);
        Assumptions.assumeTrue(!requests.isEmpty(), "No pending requests in DB to test with");

        ServiceRequest[] reqArr = requests.toArray(new ServiceRequest[0]);
        int budgetMinutes = 240;

        DynamicProgramming.KnapsackResult result = DynamicProgramming.requestKnapsack(reqArr, budgetMinutes);

        int totalCost = 0, totalValue = 0;
        for (int i = 0; i < reqArr.length; i++) {
            if (result.selectedItems[i]) {
                totalCost += DynamicProgramming.estimateCost(reqArr[i]);
                totalValue += reqArr[i].getUrgencyLevel();
            }
        }

        assertTrue(totalCost <= budgetMinutes,
                "selected real requests must not exceed the " + budgetMinutes + "-minute budget");
        assertEquals(result.maxValue, totalValue,
                "reported maxValue must match the actual urgency sum of selected real requests");

        System.out.println("[DynamicProgrammingTest] Used " + totalCost + "/" + budgetMinutes
                + " minutes, value=" + totalValue + " across " + reqArr.length + " real requests.");
    }

    /**
     * Replaces testNegativeBudgetThrows. This is pure input validation on
     * optimalRouteBudget's contract (negative budget is always illegal,
     * regardless of what the arrays contain), so it uses minimal literal
     * arrays rather than DB rows — there's no real-world data that would
     * make this case meaningfully different.
     */
    @Test
    void testNegativeBudgetThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> DynamicProgramming.optimalRouteBudget(new int[]{1}, new int[]{1}, -5));
    }

    /**
     * Replaces testMismatchedArrayLengthsThrows. Same reasoning as above —
     * pure input-validation contract test, independent of any domain data.
     */
    @Test
    void testMismatchedArrayLengthsThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> DynamicProgramming.optimalRouteBudget(new int[]{1, 2}, new int[]{1}, 10));
    }
}