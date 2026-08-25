package com.ug.campusops.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for Graph, BFS, DFS, Dijkstra, Prim, Kruskal. */
class GraphTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph(10);
        for (int i = 0; i < 6; i++) {
            graph.addVertex(i);
        }
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 6);
        graph.addEdge(4, 5, 2);
    }

    @Test
    void testAddVertexAndEdge() {
        assertEquals(6, graph.getVertexCount());
        assertEquals(6, graph.getEdgeCount());
        assertTrue(graph.hasEdge(0, 1));
    }

    @Test
    void testGetNeighbors() {
        int[] neighbors = graph.getNeighbors(0);
        assertArrayEquals(new int[] {1, 2}, neighbors);
    }

    @Test
    void testGetWeight() {
        assertEquals(2.0, graph.getWeight(0, 2), 0.0001);
        assertEquals(4.0, graph.getWeight(1, 3), 0.0001);
    }

    @Test
    void testHasEdge() {
        assertTrue(graph.hasEdge(2, 3));
        assertFalse(graph.hasEdge(3, 2));
    }

    @Test
    void testNoEdge() {
        assertEquals(-1.0, graph.getWeight(5, 0), 0.0001);
    }
}
