package com.ug.campusops.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph(10);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 6);
    }

    @Test
    void traverseVisitsLayerByLayer() {
        assertArrayEquals(new int[] {0, 1, 2, 3, 4}, BFS.traverse(graph, 0));
    }

    @Test
    void traverseFromIsolatedVertexVisitsOnlyItself() {
        graph.addVertex(9);
        assertArrayEquals(new int[] {9}, BFS.traverse(graph, 9));
    }

    @Test
    void findReachableMatchesTraverse() {
        assertArrayEquals(BFS.traverse(graph, 0), BFS.findReachable(graph, 0));
    }
}
