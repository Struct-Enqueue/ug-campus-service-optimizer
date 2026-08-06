package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for BTree. Required: search trace, node split explanation. */
class BTreeTest {

    private BTree<Integer> btree;

    @BeforeEach
    void setUp() { btree = new BTree<>(3); } // minimum degree t=3

    @Test void testInsertAndSearch() { /* TODO */ }
    @Test void testNodeSplit() { /* TODO: insert enough keys to trigger a split — document for report */ }
    @Test void testSearchNotFound() { /* TODO */ }
    @Test void testTraverse() { /* TODO */ }
    @Test void testEmptyTree() { /* TODO */ }
    @Test void testSearchTrace() { /* TODO: trace which nodes are visited during search */ }
}
