package com.ug.campusops.graph;

import java.util.HashSet;

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
        inTree = new HashSet<Integer>()
        result = new MSTResult(graph.getVertexCount() - 1)
        pick any vertex (e.g. graph.getVertexIds()[0]) as start, add to inTree
        pq = new MyPriorityQueue<Edge>()   // small wrapper class: {fromId, toId, weight}, compareTo by weight
        push all edges out of start into pq

        while inTree.size() < graph.getVertexCount():
            edge = pq.extractMin()
            if edge.toId already in inTree: continue      // skip, would form a cycle
            add edge.toId to inTree
            record edge into result (edges[][], weights[], edgeCount++, totalCost += weight)
            for each neighbor of edge.toId not in inTree:
                push new Edge(edge.toId, neighbor, graph.getWeight(edge.toId, neighbor))

        return result
                
     
        throw new UnsupportedOperationException("Prim.minimumSpanningTree() not yet implemented");
    }
}
