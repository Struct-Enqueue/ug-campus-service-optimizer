package com.ug.campusops.graph;

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
        // TODO: Feature 5 team — implement this
        //   Use a stack (MyStack from Feature 2) and a visited set.
        //   OR use recursion (which implicitly uses the call stack).
        //   1. Push startId, mark visited
        //   2. While stack not empty: pop, record, push unvisited neighbors
        throw new UnsupportedOperationException("DFS.traverse() not yet implemented");
    }

    /**
     * Returns all locations reachable from the starting location.
     *
     * @param graph   the campus graph
     * @param startId the starting location ID
     * @return array of reachable location IDs
     */
    public static int[] findReachable(Graph graph, int startId) {
        // TODO: Feature 5 team — implement this
        throw new UnsupportedOperationException("DFS.findReachable() not yet implemented");
    }
}
