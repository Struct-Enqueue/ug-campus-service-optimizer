package com.ug.campusops.graph;

import com.ug.campusops.datastructures.MyQueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        MyQueue<Integer> queue = new MyQueue<>();

        queue.enqueue(startId);
        visited.add(startId);

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            order.add(current);
            for (int neighbor : graph.getNeighbors(current)) {
                if (visited.add(neighbor)) {
                    queue.enqueue(neighbor);
                }
            }
        }

        int[] result = new int[order.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = order.get(i);
        }
        return result;
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
