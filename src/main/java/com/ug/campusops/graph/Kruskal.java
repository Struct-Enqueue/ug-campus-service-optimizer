package com.ug.campusops.graph;

import com.ug.campusops.datastructures.DisjointSet;
import com.ug.campusops.graph.Prim.MSTResult;

import java.util.ArrayList;
import java.util.List;

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
        int[] vertexIds = graph.getVertexIds();
        List<int[]> edges = new ArrayList<>();
        int maxVertexId = 0;

        for (int id : vertexIds) {
            maxVertexId = Math.max(maxVertexId, id);
            for (int neighbor : graph.getNeighbors(id)) {
                maxVertexId = Math.max(maxVertexId, neighbor);
                edges.add(new int[] {id, neighbor});
            }
        }
        edges.sort((a, b) -> Double.compare(graph.getWeight(a[0], a[1]), graph.getWeight(b[0], b[1])));

        DisjointSet ds = new DisjointSet(maxVertexId + 1);
        MSTResult result = new MSTResult(Math.max(0, graph.getVertexCount() - 1));

        for (int[] edge : edges) {
            int fromId = edge[0];
            int toId = edge[1];
            if (ds.find(fromId) != ds.find(toId)) {
                ds.union(fromId, toId);
                double weight = graph.getWeight(fromId, toId);
                result.edges[result.edgeCount][0] = fromId;
                result.edges[result.edgeCount][1] = toId;
                result.weights[result.edgeCount] = weight;
                result.edgeCount++;
                result.totalCost += weight;
            }
            if (result.edgeCount == graph.getVertexCount() - 1) {
                break;
            }
        }

        return result;
    }
}
