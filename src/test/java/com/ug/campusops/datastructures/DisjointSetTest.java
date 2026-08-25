package com.ug.campusops.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for DisjointSet. Required: Kruskal connectivity trace. */
class DisjointSetTest {

    private DisjointSet ds;

    @BeforeEach
    void setUp() { ds = new DisjointSet(10); }

    @Test
    void testMakeSet() {
        assertEquals(10, ds.getCount());
        assertFalse(ds.connected(0, 1));
    }

    @Test
    void testUnionAndFind() {
        assertTrue(ds.union(0, 1));
        assertEquals(ds.find(0), ds.find(1));
    }

    @Test
    void testConnected() {
        ds.union(1, 2);
        ds.union(2, 3);

        assertTrue(ds.connected(1, 3));
        assertFalse(ds.connected(0, 3));
    }

    @Test
    void testPathCompression() {
        ds.union(0, 1);
        ds.union(1, 2);
        ds.union(2, 3);

        assertEquals(ds.find(0), ds.find(3));
    }

    @Test
    void testUnionByRank() {
        ds.union(0, 1);
        ds.union(2, 3);
        ds.union(0, 2);

        assertTrue(ds.connected(0, 3));
    }

    @Test
    void testGetCount() {
        assertEquals(10, ds.getCount());
        ds.union(0, 1);
        ds.union(2, 3);
        assertEquals(8, ds.getCount());
    }

    @Test
    void testKruskalConnectivityTrace() {
        ds.union(0, 1);
        ds.union(1, 2);
        ds.union(3, 4);
        ds.union(4, 5);
        ds.union(2, 3);

        assertTrue(ds.connected(0, 5));
        assertEquals(5, ds.getCount());
    }
}
