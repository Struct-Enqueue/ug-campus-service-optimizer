package com.ug.campusops.graph;

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
        // TODO: Feature 5 team — implement this
        //   Use MyPriorityQueue (from Feature 4) as the min-priority queue.
        //   Remember: weight = distance × trafficFactor + routePenalty (from index-number rule)
        throw new UnsupportedOperationException("Dijkstra.shortestPath() not yet implemented");
    }

    /**
     * Computes shortest paths from source to ALL other locations.
     *
     * @param graph    the campus graph
     * @param sourceId the starting location ID
     * @return a ShortestPathResult with all distances and predecessors filled
     */
    public static ShortestPathResult allShortestPaths(Graph graph, int sourceId) {
        // TODO: Feature 5 team — implement this (same as above, but don't stop at destination)
        throw new UnsupportedOperationException("Dijkstra.allShortestPaths() not yet implemented");
    }
}
