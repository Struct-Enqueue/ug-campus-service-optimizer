package com.ug.campusops.graph;

import com.ug.campusops.datastructures.MyPriorityQueue;

import java.util.HashSet;
import java.util.Set;

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
        MSTResult result = new MSTResult(Math.max(0, graph.getVertexCount() - 1));
        int[] vertexIds = graph.getVertexIds();
        if (vertexIds.length == 0) {
            return result;
        }

        Set<Integer> inTree = new HashSet<>();
        MyPriorityQueue<Edge> pq = new MyPriorityQueue<>();

        int start = vertexIds[0];
        inTree.add(start);
        pushEdgesFrom(graph, start, inTree, pq);

        while (inTree.size() < graph.getVertexCount() && !pq.isEmpty()) {
            Edge edge = pq.extractMin();
            if (inTree.contains(edge.toId)) {
                continue;
            }
            inTree.add(edge.toId);
            result.edges[result.edgeCount][0] = edge.fromId;
            result.edges[result.edgeCount][1] = edge.toId;
            result.weights[result.edgeCount] = edge.weight;
            result.edgeCount++;
            result.totalCost += edge.weight;

            pushEdgesFrom(graph, edge.toId, inTree, pq);
        }

        return result;
    }

    private static void pushEdgesFrom(Graph graph, int fromId, Set<Integer> inTree, MyPriorityQueue<Edge> pq) {
        for (int neighbor : graph.getNeighbors(fromId)) {
            if (!inTree.contains(neighbor)) {
                pq.insert(new Edge(fromId, neighbor, graph.getWeight(fromId, neighbor)));
            }
        }
    }

    private static class Edge implements Comparable<Edge> {
        final int fromId;
        final int toId;
        final double weight;

        Edge(int fromId, int toId, double weight) {
            this.fromId = fromId;
            this.toId = toId;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(weight, other.weight);
        }
    }
}
