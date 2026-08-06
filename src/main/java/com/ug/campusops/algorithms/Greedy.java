package com.ug.campusops.algorithms;

import com.ug.campusops.graph.Graph;
import com.ug.campusops.model.Resource;
import com.ug.campusops.model.ServiceRequest;

/**
 * Greedy algorithm implementations for resource assignment and routing.
 * A greedy algorithm makes the best local choice at each step.
 *
 * Feature 7 — Optimisation Engine
 *
 * Required evidence:
 *   - Working greedy implementation
 *   - At least ONE counterexample where greedy fails (produces suboptimal result)
 */
public class Greedy {

    /**
     * Greedy nearest-resource assignment: for each request, assign the closest
     * available resource. Fast, but may not be globally optimal.
     *
     * @param requests  array of pending service requests
     * @param resources array of available resources
     * @param graph     the campus graph (for distance calculation)
     * @return array of resource IDs assigned to each request (parallel array)
     */
    public static int[] nearestResourceAssignment(ServiceRequest[] requests,
                                                   Resource[] resources,
                                                   Graph graph) {
        // TODO: Feature 7 team — implement this
        //   For each request:
        //     1. Find all available resources
        //     2. Pick the one closest to the request's location (use Dijkstra or direct weight)
        //     3. Assign it and mark it busy
        throw new UnsupportedOperationException("Greedy.nearestResourceAssignment() not yet implemented");
    }

    /**
     * Demonstrates a case where the greedy approach gives a WRONG or SUBOPTIMAL answer.
     * Required by the brief as evidence of understanding greedy limitations.
     *
     * Example scenario: two requests close together but far from available resources,
     * greedy assigns the single nearby resource to the first request, leaving the
     * second request with a much farther resource — when swapping assignments would
     * have been better overall.
     *
     * @return a String describing the counterexample with numbers
     */
    public static String greedyCounterExample() {
        // TODO: Feature 7 team — implement this
        //   Build a small example (3-5 locations, 2-3 resources, 2+ requests)
        //   Show greedy total cost vs optimal total cost
        throw new UnsupportedOperationException("Greedy.greedyCounterExample() not yet implemented");
    }
}
