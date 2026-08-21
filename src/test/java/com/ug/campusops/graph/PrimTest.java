package com.ug.campusops.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph(10);
        graph.addEdge(0, 1, 5);
        graph.addEdge(1, 0, 5);
        graph.addEdge(0, 2, 2);
        graph.addEdge(2, 0, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(3, 1, 4);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 2, 1);
        graph.addEdge(3, 4, 6);
        graph.addEdge(4, 3, 6);
    }

    @Test
    void connectsAllVerticesWithMinimumCost() {
        Prim.MSTResult result = Prim.minimumSpanningTree(graph);
        assertEquals(4, result.edgeCount);
        assertEquals(13.0, result.totalCost, 0.0001);
    }
}
