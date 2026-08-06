package com.ug.campusops.graph;

/**
 * Breadth-First Search (BFS) on the campus graph.
 * Explores locations layer by layer — first all direct neighbors, then their neighbors, etc.
 * Used to find all locations reachable from a given starting point.
 *
 * Feature 5 — Graph Algorithms
 * Depends on: Feature 4's Graph class
 *
 * Required evidence: trace table and graph diagram showing BFS traversal order.
 */
public class BFS {

    /**
     * Performs BFS starting from the given location and returns the traversal order.
     *
     * @param graph   the campus graph
     * @param startId the starting location ID
     * @return array of location IDs in BFS visit order
     */
    public static int[] traverse(Graph graph, int startId) {
        // TODO: Feature 5 team — implement this
        //   Use a queue (MyQueue from Feature 2) and a visited set.
        //   1. Enqueue startId, mark visited
        //   2. While queue not empty: dequeue, record, enqueue unvisited neighbors
        throw new UnsupportedOperationException("BFS.traverse() not yet implemented");
    }

    /**
     * Returns all locations reachable from the starting location.
     *
     * @param graph   the campus graph
     * @param startId the starting location ID
     * @return array of reachable location IDs
     */
    public static int[] findReachable(Graph graph, int startId) {
        // TODO: Feature 5 team — implement this (same as traverse, but return the visited set)
        throw new UnsupportedOperationException("BFS.findReachable() not yet implemented");
    }
}
