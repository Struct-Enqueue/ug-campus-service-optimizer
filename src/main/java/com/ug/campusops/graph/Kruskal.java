package com.ug.campusops.graph;

import com.ug.campusops.datastructures.DisjointSet;
import com.ug.campusops.graph.Prim.MSTResult;

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
        collect every edge (fromId, toId, weight) by iterating graph.getVertexIds() and calling
        graph.getNeighbors(id) + graph.getWeight(id, neighbor) for each
        sort edges by weight ascending
        ds = new DisjointSet(maxVertexId + 1)   // size big enough to cover every vertex id used
        result = new MSTResult(graph.getVertexCount() - 1)

        for each edge in sorted order:
            if ds.find(edge.fromId) != ds.find(edge.toId):
                ds.union(edge.fromId, edge.toId)
                record edge into result
            if result.edgeCount == graph.getVertexCount() - 1: break

        return result
     
        throw new UnsupportedOperationException("Kruskal.minimumSpanningTree() not yet implemented");
    }
}
