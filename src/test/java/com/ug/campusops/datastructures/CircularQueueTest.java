package com.ug.campusops.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for CircularQueue.
 *
 * Required coverage:
 * - Basic enqueue/dequeue operations
 * - Circular wrap-around behavior
 * - Full queue edge case
 * - Empty queue edge case
 * - Front and rear index tracking
 */
class CircularQueueTest {

    private static final int CAPACITY = 5;

    private CircularQueue<Integer> cq;

    @BeforeEach
    void setUp() {
        cq = new CircularQueue<>(CAPACITY);
    }

    /**
     * Tests basic enqueue and dequeue operations.
     */
    @Test
    void testEnqueueDequeue() {
        cq.enqueue(10);
        cq.enqueue(20);

        assertEquals(10, cq.dequeue());
        assertEquals(20, cq.dequeue());

        assertTrue(cq.isEmpty());
    }

    /**
     * Tests circular wrap-around behavior.
     */
    @Test
    void testWrapAround() {

        // Fill all 5 slots
        for (int i = 1; i <= CAPACITY; i++) {
            cq.enqueue(i * 10);
        }

        // Remove two elements
        assertEquals(10, cq.dequeue());
        assertEquals(20, cq.dequeue());

        // Add two more elements.
        // Rear should wrap around.
        cq.enqueue(60);
        cq.enqueue(70);

        // Logical queue:
        // 30, 40, 50, 60, 70
        assertEquals(30, cq.dequeue());
        assertEquals(40, cq.dequeue());
        assertEquals(50, cq.dequeue());
        assertEquals(60, cq.dequeue());
        assertEquals(70, cq.dequeue());

        assertTrue(cq.isEmpty());
    }

    /**
     * Tests the full queue condition.
     */
    @Test
    void testIsFull() {

        assertFalse(
                cq.isFull(),
                "Newly created queue should not be full"
        );

        for (int i = 0; i < CAPACITY; i++) {
            cq.enqueue(i);
        }

        assertTrue(
                cq.isFull(),
                "Queue should be full after " + CAPACITY + " enqueues"
        );

        cq.dequeue();

        assertFalse(
                cq.isFull(),
                "Queue should not be full after one dequeue"
        );
    }

    /**
     * Tests the empty queue condition.
     */
    @Test
    void testIsEmpty() {

        assertTrue(
                cq.isEmpty(),
                "Newly created queue should be empty"
        );

        cq.enqueue(42);

        assertFalse(
                cq.isEmpty(),
                "Queue should not be empty after enqueue"
        );

        cq.dequeue();

        assertTrue(
                cq.isEmpty(),
                "Queue should be empty again after dequeue"
        );
    }

    /**
     * Tests that enqueueing into a full queue throws an exception.
     */
    @Test
    void testEnqueueWhenFull() {

        for (int i = 0; i < CAPACITY; i++) {
            cq.enqueue(i);
        }

        assertThrows(
                IllegalStateException.class,
                () -> cq.enqueue(42),
                "Enqueueing to a full queue should throw an exception"
        );
    }

    /**
     * Tests that dequeueing from an empty queue throws
     * NoSuchElementException, matching CircularQueue's implementation.
     */
    @Test
    void testDequeueWhenEmpty() {

        assertThrows(
                NoSuchElementException.class,
                () -> cq.dequeue(),
                "Dequeueing from an empty queue should throw an exception"
        );
    }

    /**
     * Tests front and rear index movement.
     *
     * This CircularQueue implementation uses -1 to represent
     * an empty queue.
     */
    @Test
    void testFrontRearIndexes() {

        // Empty queue indexes.
        assertEquals(0, cq.getFrontIndex());
        assertEquals(-1, cq.getRearIndex());

        // First enqueue.
        cq.enqueue(1);

        // First element should occupy index 0.
        assertEquals(0, cq.getFrontIndex());
        assertEquals(0, cq.getRearIndex());

        // Second enqueue.
        cq.enqueue(2);

        // Rear should move to index 1.
        assertEquals(0, cq.getFrontIndex());
        assertEquals(1, cq.getRearIndex());

        // Dequeue 1.
        // Front should move to index 1.
        assertEquals(1, cq.dequeue());

        assertEquals(1, cq.getFrontIndex());
        assertEquals(1, cq.getRearIndex());

        /*
         * Current state:
         *
         * Index:   0    1    2    3    4
         *          -    2    -    -    -
         *
         * front = 1
         * rear  = 1
         */

        cq.enqueue(3);
        assertEquals(2, cq.getRearIndex());

        cq.enqueue(4);
        assertEquals(3, cq.getRearIndex());

        cq.enqueue(5);
        assertEquals(4, cq.getRearIndex());

        cq.enqueue(6);

        // Rear should wrap from 4 to 0.
        assertEquals(
                0,
                cq.getRearIndex(),
                "Rear should wrap around to 0"
        );

        assertTrue(
                cq.isFull(),
                "Queue should be full"
        );

        // Dequeue 2.
        // Front moves from 1 to 2.
        assertEquals(2, cq.dequeue());

        assertEquals(
                2,
                cq.getFrontIndex(),
                "Front should move to 2 after dequeue"
        );
    }
}