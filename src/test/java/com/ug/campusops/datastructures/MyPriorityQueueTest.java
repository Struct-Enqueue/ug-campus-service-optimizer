package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for MyPriorityQueue. Required: dispatch order trace, insert/extract at multiple sizes. */
class MyPriorityQueueTest {

    private MyPriorityQueue<Integer> pq;

    @BeforeEach
    void setUp() { pq = new MyPriorityQueue<>(); }

    @Test void testInsertAndExtractMin() { /* TODO */ }
    @Test void testHeapOrder() { /* TODO: insert 5,1,3,2,4 → extractMin should give 1,2,3,4,5 */ }
    @Test void testPeek() { /* TODO */ }
    @Test void testIsEmpty() { /* TODO */ }
    @Test void testExtractMinEmpty() { /* TODO: should throw NoSuchElementException */ }
    @Test void testSingleElement() { /* TODO */ }
    @Test void testDuplicateKeys() { /* TODO: insert same value multiple times */ }
    @Test void testDispatchOrderTrace() { /* TODO: insert requests by urgency, extract in order — trace for report */ }
}
