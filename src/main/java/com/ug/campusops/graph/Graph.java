package com.ug.campusops.graph;

/**
 * Graph representation of the UG Legon campus network.
 * Stores locations as vertices and routes as weighted edges using BOTH:
 *   - Adjacency List (for efficient neighbor traversal)
 *   - Adjacency Matrix (for fast O(1) edge-weight lookup)
 *
 * This is a custom implementation — do NOT use any built-in graph libraries.
 *
 * Feature 4 — Priority Queue, Graph Core & Disjoint Set
 *
 * AGREED METHOD NAMES (do not rename — other features depend on these):
 *   addVertex(), addEdge(), getNeighbors(), getWeight(), getVertexCount(), getEdgeCount()
 */
public class Graph {

    // TODO: Feature 4 team — choose internal storage:
    //   - adjacencyList: array/map of linked lists (locationId → list of {neighborId, weight})
    //   - adjacencyMatrix: 2D array where matrix[i][j] = weight (0 or -1 if no edge)

    private int vertexCount;
    private int edgeCount;

    /**
     * Creates an empty graph that can hold up to maxVertices vertices.
     *
     * @param maxVertices maximum number of locations the graph can hold
     */
    public Graph(int maxVertices) {
        this.vertexCount = 0;
        this.edgeCount = 0;
        // TODO: Feature 4 team — initialize adjacency list and adjacency matrix
    }

    /**
     * Adds a vertex (location) to the graph.
     *
     * @param locationId the unique ID of the location
     */
    public void addVertex(int locationId) {
        // TODO: Feature 4 team — implement this
        throw new UnsupportedOperationException("Graph.addVertex() not yet implemented");
    }

    /**
     * Adds a weighted directed edge from one location to another.
     * For undirected roads, call this twice (once in each direction).
     *
     * @param fromId the source location ID
     * @param toId   the destination location ID
     * @param weight the edge weight (e.g. distance in meters or travel time)
     */
    public void addEdge(int fromId, int toId, double weight) {
        // TODO: Feature 4 team — implement this (update BOTH adjacency list AND matrix)
        throw new UnsupportedOperationException("Graph.addEdge() not yet implemented");
    }

    /**
     * Returns the neighbor IDs of the specified location.
     * This is the most-used method — Feature 5 (BFS, DFS, Dijkstra) calls it heavily.
     *
     * @param locationId the location to get neighbors for
     * @return array of neighbor location IDs (or empty array if none)
     */
    public int[] getNeighbors(int locationId) {
        // TODO: Feature 4 team — implement this (read from adjacency list)
        throw new UnsupportedOperationException("Graph.getNeighbors() not yet implemented");
    }

    /**
     * Returns the weight of the edge between two locations.
     *
     * @param fromId source location ID
     * @param toId   destination location ID
     * @return the edge weight, or -1 if no edge exists
     */
    public double getWeight(int fromId, int toId) {
        // TODO: Feature 4 team — implement this (read from adjacency matrix for O(1))
        throw new UnsupportedOperationException("Graph.getWeight() not yet implemented");
    }

    /**
     * Returns true if there is an edge from fromId to toId.
     *
     * @param fromId source location ID
     * @param toId   destination location ID
     */
    public boolean hasEdge(int fromId, int toId) {
        // TODO: Feature 4 team — implement this
        throw new UnsupportedOperationException("Graph.hasEdge() not yet implemented");
    }

    /** Returns the number of vertices (locations) in the graph. */
    public int getVertexCount() { return vertexCount; }

    /** Returns the number of edges (routes) in the graph. */
    public int getEdgeCount() { return edgeCount; }
}
