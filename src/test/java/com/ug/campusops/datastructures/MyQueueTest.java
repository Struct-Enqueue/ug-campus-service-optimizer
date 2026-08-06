package com.ug.campusops.datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for MyQueue. Required: normal, boundary, invalid input, FIFO order. */
class MyQueueTest {

    private MyQueue<Integer> queue;

    @BeforeEach
    void setUp() { queue = new MyQueue<>(); }

    @Test void testEnqueueAndDequeue() { /* TODO */ }
    @Test void testFIFOOrder() { /* TODO: enqueue 1,2,3 → dequeue should give 1,2,3 */ }
    @Test void testPeek() { /* TODO */ }
    @Test void testIsEmpty() { /* TODO */ }
    @Test void testDequeueEmpty() { /* TODO: should throw NoSuchElementException */ }
    @Test void testSingleElement() { /* TODO */ }
}
