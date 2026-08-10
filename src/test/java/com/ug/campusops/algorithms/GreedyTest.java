package com.ug.campusops.algorithms;

import com.ug.campusops.graph.Graph;
import com.ug.campusops.model.Resource;
import com.ug.campusops.model.ServiceRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Greedy.
 *
 * IMPORTANT: nearestResourceAssignment() depends on Graph.getWeight(), which
 * Feature 4 hasn't implemented yet (it currently throws
 * UnsupportedOperationException). Rather than wait, this test file defines a
 * throwaway FakeGraph subclass that overrides just the methods we call, with
 * a simple in-memory implementation good enough to test OUR logic.
 *
 * This FakeGraph lives only in this test file — it does not touch or replace
 * the real Graph.java. Once Feature 4 finishes their implementation, this
 * test still passes unchanged (it only relies on the public method contract
 * already agreed in the team plan: getWeight(fromId, toId) -> distance or -1).
 */
class GreedyTest {

    /** Minimal in-memory stand-in for Graph, for testing only. */
    static class FakeGraph extends Graph {
        private final Map<Long, Double> weights = new HashMap<>();

        FakeGraph() {
            super(100);
        }

        void setWeight(int fromId, int toId, double weight) {
            weights.put(key(fromId, toId), weight);
        }

        @Override
        public double getWeight(int fromId, int toId) {
            return weights.getOrDefault(key(fromId, toId), -1.0);
        }

        private long key(int fromId, int toId) {
            return ((long) fromId << 32) | (toId & 0xffffffffL);
        }
    }

    @Test
    void testGreedyCounterExampleRunsAndShowsGap() {
        String result = Greedy.greedyCounterExample();
        assertNotNull(result);
        assertTrue(result.contains("600"), "should report greedy total of 600");
        assertTrue(result.contains("230"), "should report optimal total of 230");
    }

    @Test
    void testNearestResourceAssignmentPicksClosest() {
        FakeGraph graph = new FakeGraph();
        // Request at location 1, two resources at locations 10 and 20
        graph.setWeight(1, 10, 50);
        graph.setWeight(1, 20, 500);

        ServiceRequest[] requests = {
            new ServiceRequest(1, 1, 1, "IT", 3, "t", "d", "pending", 0)
        };
        Resource[] resources = {
            new Resource(1, "Technician", 10, 1, "available"),
            new Resource(2, "Technician", 20, 1, "available")
        };

        int[] assignments = Greedy.nearestResourceAssignment(requests, resources, graph);

        assertEquals(1, assignments[0], "should assign the closer resource (id 1, distance 50)");
        assertEquals("busy", resources[0].getAvailabilityStatus(), "assigned resource should be marked busy");
        assertEquals("available", resources[1].getAvailabilityStatus(), "unused resource should stay available");
    }

    @Test
    void testNearestResourceAssignmentReturnsMinusOneWhenNoneAvailable() {
        FakeGraph graph = new FakeGraph();
        graph.setWeight(1, 10, 50);

        ServiceRequest[] requests = {
            new ServiceRequest(1, 1, 1, "IT", 3, "t", "d", "pending", 0)
        };
        Resource[] resources = {
            new Resource(1, "Technician", 10, 1, "busy") // already busy
        };

        int[] assignments = Greedy.nearestResourceAssignment(requests, resources, graph);
        assertEquals(-1, assignments[0]);
    }

    @Test
    void testNearestResourceAssignmentDoesNotDoubleBookSameResource() {
        FakeGraph graph = new FakeGraph();
        graph.setWeight(1, 10, 50);
        graph.setWeight(2, 10, 60); // same resource is also closest to request 2

        ServiceRequest[] requests = {
            new ServiceRequest(1, 1, 1, "IT", 3, "t", "d", "pending", 0),
            new ServiceRequest(2, 2, 1, "IT", 3, "t", "d", "pending", 0)
        };
        Resource[] resources = {
            new Resource(1, "Technician", 10, 1, "available")
        };

        int[] assignments = Greedy.nearestResourceAssignment(requests, resources, graph);
        assertEquals(1, assignments[0], "first request should get the only resource");
        assertEquals(-1, assignments[1], "second request should get nothing left to assign");
    }
}
