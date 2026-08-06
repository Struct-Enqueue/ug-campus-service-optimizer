package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for BST. Required: search path trace, sorted inorder output. */
class BSTTest {

    private BST<Integer, String> bst;

    @BeforeEach
    void setUp() { bst = new BST<>(); }

    @Test void testInsertAndSearch() { /* TODO */ }
    @Test void testInorderTraversal() { /* TODO: verify sorted output */ }
    @Test void testDelete() { /* TODO: delete leaf, node with 1 child, node with 2 children */ }
    @Test void testSearchNotFound() { /* TODO: should return null */ }
    @Test void testHeight() { /* TODO */ }
    @Test void testEmptyTree() { /* TODO */ }
    @Test void testDuplicateKey() { /* TODO: should update value */ }
    @Test void testSearchPathTrace() { /* TODO: trace path taken during search for report */ }
}
