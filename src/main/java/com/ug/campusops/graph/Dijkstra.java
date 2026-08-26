package com.ug.campusops.graph;

import com.ug.campusops.datastructures.MyPriorityQueue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Dijkstra's shortest path algorithm on the campus graph.
 * Finds the fastest/shortest route between two campus locations,
 * considering road distance, travel time, and traffic conditions.
 *
 * Feature 5 — Graph Algorithms
 * Depends on: Feature 4's Graph class and MyPriorityQueue
 *
 * Required evidence: distance table and predecessor path trace.
 *
 * Step-by-step (from the Planning team's pseudocode):
 *   1. Start at the repair person's current location, distance = 0
 *   2. Look at every place directly connected to where we are
 *   3. For each connected place: distance so far + (route distance × traffic factor + route penalty)
 *   4. If this is shorter than what we found before, remember it
 *   5. Move to whichever unvisited place has the shortest known distance, repeat
 *   6. Stop once we reach the destination — we now have the fastest route
 */
public class Dijkstra {

    /**
     * Holds the result of a shortest-path computation.
     */
    public static class ShortestPathResult {
        public int[] predecessors;  // predecessors[i] = previous node on shortest path to i
        public double[] distances;  // distances[i] = shortest distance from source to i
        public int source;
        public int destination;

        public ShortestPathResult(int numVertices) {
            this.predecessors = new int[numVertices];
            this.distances = new double[numVertices];
        }
    }

    /**
     * Finds the shortest path from source to destination.
     *
     * @param graph  the campus graph
     * @param sourceId the starting location ID
     * @param destId   the destination location ID
     * @return a ShortestPathResult containing distances and predecessor chain
     */
    public static ShortestPathResult shortestPath(Graph graph, int sourceId, int destId) {
        return run(graph, sourceId, destId);
    }

    /**
     * Computes shortest paths from source to ALL other locations.
     *
     * @param graph    the campus graph
     * @param sourceId the starting location ID
     * @return a ShortestPathResult with all distances and predecessors filled
     */
    public static ShortestPathResult allShortestPaths(Graph graph, int sourceId) {
        return run(graph, sourceId, -1);
    }

    private static class Node implements Comparable<Node> {
        final int id;
        final double distance;

        Node(int id, double distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(distance, other.distance);
        }
    }

    private static ShortestPathResult run(Graph graph, int sourceId, int destId) {
        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        MyPriorityQueue<Node> queue = new MyPriorityQueue<>();

        dist.put(sourceId, 0.0);
        queue.insert(new Node(sourceId, 0.0));
        int maxId = Math.max(sourceId, destId);

        while (!queue.isEmpty()) {
            Node current = queue.extractMin();
            if (!visited.add(current.id)) {
                continue;
            }
            maxId = Math.max(maxId, current.id);
            if (destId >= 0 && current.id == destId) {
                break;
            }

            for (int neighbor : graph.getNeighbors(current.id)) {
                maxId = Math.max(maxId, neighbor);
                double newDist = dist.get(current.id) + graph.getWeight(current.id, neighbor);
                if (newDist < dist.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current.id);
                    queue.insert(new Node(neighbor, newDist));
                }
            }
        }

        ShortestPathResult result = new ShortestPathResult(maxId + 1);
        result.source = sourceId;
        result.destination = destId;
        Arrays.fill(result.distances, Double.POSITIVE_INFINITY);
        Arrays.fill(result.predecessors, -1);
        for (Map.Entry<Integer, Double> entry : dist.entrySet()) {
            result.distances[entry.getKey()] = entry.getValue();
        }
        for (Map.Entry<Integer, Integer> entry : prev.entrySet()) {
            result.predecessors[entry.getKey()] = entry.getValue();
        }
        return result;
    }
}
