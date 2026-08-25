package com.ug.campusops.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

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
    void shortestPathPicksCheaperRoute() {
        Dijkstra.ShortestPathResult result = Dijkstra.shortestPath(graph, 0, 3);
        assertEquals(3.0, result.distances[3], 0.0001);
        assertEquals(2, result.predecessors[3]);
        assertEquals(0, result.predecessors[2]);
    }

    @Test
    void shortestPathToUnreachableVertexIsInfinite() {
        graph.addVertex(9);
        Dijkstra.ShortestPathResult result = Dijkstra.shortestPath(graph, 0, 9);
        assertEquals(Double.POSITIVE_INFINITY, result.distances[9]);
        assertEquals(-1, result.predecessors[9]);
    }

    @Test
    void allShortestPathsCoversEveryReachableVertex() {
        Dijkstra.ShortestPathResult result = Dijkstra.allShortestPaths(graph, 0);
        assertEquals(0.0, result.distances[0], 0.0001);
        assertEquals(2.0, result.distances[2], 0.0001);
        assertEquals(3.0, result.distances[3], 0.0001);
        assertEquals(9.0, result.distances[4], 0.0001);
    }
}
