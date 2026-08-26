package com.ug.campusops.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Depth-First Search (DFS) on the campus graph.
 * Explores as deep as possible down one path before backtracking.
 * Used to discover connected components and check reachability.
 *
 * Feature 5 — Graph Algorithms
 * Depends on: Feature 4's Graph class
 *
 * Required evidence: trace table and graph diagram showing DFS traversal order.
 */
public class DFS {

    /**
     * Performs DFS starting from the given location and returns the traversal order.
     *
     * @param graph   the campus graph
     * @param startId the starting location ID
     * @return array of location IDs in DFS visit order
     */
    public static int[] traverse(Graph graph, int startId) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        visit(graph, startId, visited, order);

        int[] result = new int[order.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = order.get(i);
        }
        return result;
    }

    private static void visit(Graph graph, int id, Set<Integer> visited, List<Integer> order) {
        visited.add(id);
        order.add(id);
        for (int neighbor : graph.getNeighbors(id)) {
            if (!visited.contains(neighbor)) {
                visit(graph, neighbor, visited, order);
            }
        }
    }

    /**
     * Returns all locations reachable from the starting location.
     *
     * @param graph   the campus graph
     * @param startId the starting location ID
     * @return array of reachable location IDs
     */
    public static int[] findReachable(Graph graph, int startId) {
        return traverse(graph, startId);
    }
}
