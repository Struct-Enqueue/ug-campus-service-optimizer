package com.ug.campusops.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<Integer, List<Integer>> adjacencyList;
    private double[][] adjacencyMatrix;
    private int vertexCount;
    private int edgeCount;
    private int maxVertices;

    /**
     * Creates an empty graph that can hold up to maxVertices vertices.
     *
     * @param maxVertices maximum number of locations the graph can hold
     */
    public Graph(int maxVertices) {
        this.maxVertices = Math.max(1, maxVertices);
        this.adjacencyList = new LinkedHashMap<>();
        this.adjacencyMatrix = new double[this.maxVertices][this.maxVertices];
        for (int i = 0; i < this.maxVertices; i++) {
            for (int j = 0; j < this.maxVertices; j++) {
                this.adjacencyMatrix[i][j] = -1.0;
            }
        }
        this.vertexCount = 0;
        this.edgeCount = 0;
    }

    /**
     * Adds a vertex (location) to the graph.
     *
     * @param locationId the unique ID of the location
     */
    public void addVertex(int locationId) {
        if (locationId < 0) {
            throw new IllegalArgumentException("Location ID cannot be negative");
        }
        ensureCapacity(locationId + 1);
        if (!adjacencyList.containsKey(locationId)) {
            adjacencyList.put(locationId, new ArrayList<>());
            vertexCount++;
        }
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
        if (fromId < 0 || toId < 0) {
            throw new IllegalArgumentException("Location IDs cannot be negative");
        }
        addVertex(fromId);
        addVertex(toId);
        ensureCapacity(Math.max(fromId, toId) + 1);

        if (!hasEdge(fromId, toId)) {
            adjacencyList.get(fromId).add(toId);
            edgeCount++;
        }

        adjacencyMatrix[fromId][toId] = weight;
    }

    /**
     * Returns the neighbor IDs of the specified location.
     * This is the most-used method — Feature 5 (BFS, DFS, Dijkstra) calls it heavily.
     *
     * @param locationId the location to get neighbors for
     * @return array of neighbor location IDs (or empty array if none)
     */
    public int[] getNeighbors(int locationId) {
        if (!adjacencyList.containsKey(locationId)) {
            return new int[0];
        }
        List<Integer> neighbors = adjacencyList.get(locationId);
        int[] result = new int[neighbors.size()];
        for (int i = 0; i < neighbors.size(); i++) {
            result[i] = neighbors.get(i);
        }
        return result;
    }

    /**
     * Returns the weight of the edge between two locations.
     *
     * @param fromId source location ID
     * @param toId   destination location ID
     * @return the edge weight, or -1 if no edge exists
     */
    public double getWeight(int fromId, int toId) {
        if (fromId < 0 || toId < 0 || fromId >= adjacencyMatrix.length || toId >= adjacencyMatrix.length) {
            return -1.0;
        }
        return adjacencyMatrix[fromId][toId];
    }

    /**
     * Returns true if there is an edge from fromId to toId.
     *
     * @param fromId source location ID
     * @param toId   destination location ID
     */
    public boolean hasEdge(int fromId, int toId) {
        return getWeight(fromId, toId) != -1.0;
    }

    /** Returns the number of vertices (locations) in the graph. */
    public int getVertexCount() { return vertexCount; }

    /** Returns the number of edges (routes) in the graph. */
    public int getEdgeCount() { return edgeCount; }

    private void ensureCapacity(int minimumSize) {
        if (minimumSize <= maxVertices) {
            return;
        }

        int newSize = Math.max(this.maxVertices * 2, minimumSize);
        double[][] newMatrix = new double[newSize][newSize];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                newMatrix[i][j] = -1.0;
            }
        }

        for (int i = 0; i < maxVertices; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, maxVertices);
        }

        this.adjacencyMatrix = newMatrix;
        this.maxVertices = newSize;
    }
}
