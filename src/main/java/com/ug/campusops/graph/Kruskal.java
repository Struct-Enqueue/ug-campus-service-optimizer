package com.ug.campusops.graph;

/**
 * Kruskal's algorithm for finding the Minimum Spanning Tree (MST) of the campus graph.
 * Sorts all edges by weight and greedily adds the cheapest edge that doesn't create a cycle.
 *
 * Feature 5 — Graph Algorithms
 * Depends on: Feature 4's Graph class and DisjointSet
 *
 * Required evidence: MST edge list and total cost, Kruskal connectivity trace.
 */
public class Kruskal {

    /**
     * Computes the MST using Kruskal's algorithm.
     *
     * @param graph the campus graph
     * @return a Prim.MSTResult containing the MST edges and total cost
     *         (reuses the same result class for consistency)
     */
    public static Prim.MSTResult minimumSpanningTree(Graph graph) {
        // TODO: Feature 5 team — implement this
        //   Uses DisjointSet (from Feature 4) to detect cycles.
        //   1. Collect all edges, sort by weight (use SortAlgorithms from Feature 6)
        //   2. For each edge (cheapest first):
        //      - If the two endpoints are in different sets (find), add edge to MST (union)
        //      - If same set, skip (would create a cycle)
        //   3. Stop when we have (V-1) edges
        throw new UnsupportedOperationException("Kruskal.minimumSpanningTree() not yet implemented");
    }
}
