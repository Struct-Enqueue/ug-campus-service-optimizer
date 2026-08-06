package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DynamicArray.
 * Required: normal case, boundary case, invalid input case, resize trace.
 */
class DynamicArrayTest {

    private DynamicArray<Integer> array;

    @BeforeEach
    void setUp() {
        array = new DynamicArray<>();
    }

    // ── Normal cases ─────────────────────────────────────────────────────

    @Test
    void testAddAndGet() {
        // TODO: add elements, verify get returns correct values
    }

    @Test
    void testInsertAtIndex() {
        // TODO: insert at beginning, middle, end
    }

    @Test
    void testRemove() {
        // TODO: remove element and verify shift
    }

    @Test
    void testSet() {
        // TODO: update element at index
    }

    @Test
    void testSize() {
        // TODO: verify size after add/remove
    }

    // ── Boundary cases ───────────────────────────────────────────────────

    @Test
    void testEmptyArray() {
        // TODO: verify isEmpty, size=0 on new array
    }

    @Test
    void testSingleElement() {
        // TODO: add one, get it, remove it
    }

    @Test
    void testResizeTrace() {
        // TODO: add elements beyond initial capacity, verify capacity doubles
        // This produces the resize trace required by the brief
    }

    // ── Invalid input cases ──────────────────────────────────────────────

    @Test
    void testGetOutOfBounds() {
        // TODO: verify IndexOutOfBoundsException
    }

    @Test
    void testRemoveOutOfBounds() {
        // TODO: verify IndexOutOfBoundsException
    }

    @Test
    void testNegativeCapacity() {
        // TODO: verify IllegalArgumentException for DynamicArray(-1)
    }
}
