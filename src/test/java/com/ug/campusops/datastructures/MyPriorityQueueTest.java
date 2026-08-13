package com.ug.campusops.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for MyPriorityQueue. Required: dispatch order trace, insert/extract at multiple sizes. */
class MyPriorityQueueTest {

    private MyPriorityQueue<Integer> pq;

    @BeforeEach
    void setUp() { pq = new MyPriorityQueue<>(); }

    @Test
    void testInsertAndExtractMin() {
        pq.insert(5);
        pq.insert(1);
        pq.insert(3);

        assertEquals(1, pq.extractMin());
        assertEquals(3, pq.extractMin());
        assertEquals(5, pq.extractMin());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testHeapOrder() {
        int[] values = {5, 1, 3, 2, 4};
        for (int value : values) {
            pq.insert(value);
        }

        assertEquals(1, pq.extractMin());
        assertEquals(2, pq.extractMin());
        assertEquals(3, pq.extractMin());
        assertEquals(4, pq.extractMin());
        assertEquals(5, pq.extractMin());
    }

    @Test
    void testPeek() {
        pq.insert(8);
        pq.insert(2);
        pq.insert(6);

        assertEquals(2, pq.peek());
        assertEquals(2, pq.extractMin());
    }

    @Test
    void testIsEmpty() {
        assertTrue(pq.isEmpty());
        pq.insert(10);
        assertFalse(pq.isEmpty());
        pq.extractMin();
        assertTrue(pq.isEmpty());
    }

    @Test
    void testExtractMinEmpty() {
        assertThrows(NoSuchElementException.class, pq::extractMin);
    }

    @Test
    void testSingleElement() {
        pq.insert(42);
        assertEquals(42, pq.peek());
        assertEquals(42, pq.extractMin());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testDuplicateKeys() {
        pq.insert(3);
        pq.insert(3);
        pq.insert(1);
        pq.insert(2);

        assertEquals(1, pq.extractMin());
        assertEquals(2, pq.extractMin());
        assertEquals(3, pq.extractMin());
        assertEquals(3, pq.extractMin());
    }

    @Test
    void testDispatchOrderTrace() {
        int[] urgency = {7, 3, 9, 1, 5, 2, 8, 4};
        for (int level : urgency) {
            pq.insert(level);
        }

        assertEquals(1, pq.extractMin());
        assertEquals(2, pq.extractMin());
        assertEquals(3, pq.extractMin());
        assertEquals(4, pq.extractMin());
        assertEquals(5, pq.extractMin());
        assertEquals(7, pq.extractMin());
        assertEquals(8, pq.extractMin());
        assertEquals(9, pq.extractMin());
    }
}
