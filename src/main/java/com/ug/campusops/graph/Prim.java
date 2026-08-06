package com.ug.campusops.graph;

/**
 * Prim's algorithm for finding the Minimum Spanning Tree (MST) of the campus graph.
 * Finds the cheapest way to connect all campus locations together.
 *
 * Feature 5 — Graph Algorithms
 * Depends on: Feature 4's Graph class and MyPriorityQueue
 *
 * Required evidence: MST edge list and total cost.
 */
public class Prim {

    /**
     * Holds the result of an MST computation.
     */
    public static class MSTResult {
        public int[][] edges;    // each row is {fromId, toId}
        public double[] weights; // weight of each MST edge
        public int edgeCount;    // number of edges in the MST
        public double totalCost; // sum of all edge weights

        public MSTResult(int maxEdges) {
            this.edges = new int[maxEdges][2];
            this.weights = new double[maxEdges];
            this.edgeCount = 0;
            this.totalCost = 0;
        }
    }

    /**
     * Computes the MST using Prim's algorithm.
     * Starts from vertex 0 (or any vertex) and greedily adds the cheapest edge
     * that connects a new vertex to the growing tree.
     *
     * @param graph the campus graph
     * @return an MSTResult containing the MST edges and total cost
     */
    public static MSTResult minimumSpanningTree(Graph graph) {
        // TODO: Feature 5 team — implement this
        //   Use MyPriorityQueue to always pick the cheapest edge next.
        //   1. Start with any vertex in the MST set
        //   2. Add all edges from MST set to non-MST vertices into priority queue
        //   3. Extract min edge, add its vertex to MST set
        //   4. Repeat until all vertices are in MST
        throw new UnsupportedOperationException("Prim.minimumSpanningTree() not yet implemented");
    }
}
