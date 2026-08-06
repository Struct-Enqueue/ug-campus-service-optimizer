package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for DisjointSet. Required: Kruskal connectivity trace. */
class DisjointSetTest {

    private DisjointSet ds;

    @BeforeEach
    void setUp() { ds = new DisjointSet(10); }

    @Test void testMakeSet() { /* TODO: each element should be its own set */ }
    @Test void testUnionAndFind() { /* TODO: union 0-1, verify find(0)==find(1) */ }
    @Test void testConnected() { /* TODO */ }
    @Test void testPathCompression() { /* TODO: verify find flattens the tree */ }
    @Test void testUnionByRank() { /* TODO: verify shorter tree goes under taller tree */ }
    @Test void testGetCount() { /* TODO: starts at n, decreases with each union */ }
    @Test void testKruskalConnectivityTrace() { /* TODO: simulate Kruskal unions — trace for report */ }
}
