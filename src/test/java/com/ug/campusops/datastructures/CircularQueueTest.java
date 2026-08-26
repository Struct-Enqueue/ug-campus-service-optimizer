package com.ug.campusops.datastructures;

import com.ug.campusops.db.DatabaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for CircularQueue using real campus data from PostgreSQL.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence:
 * - FIFO enqueue/dequeue behavior
 * - Full/empty edge cases
 * - Front/rear movement
 * - Circular wrap-around
 * - Use of PostgreSQL campus data
 */
class CircularQueueTest {

    private static final int CAPACITY = 5;

    private CircularQueue<String> queue;
    private DatabaseConnector db;

    @BeforeEach
    void setUp() {
        queue = new CircularQueue<>(CAPACITY);
        db = new DatabaseConnector();
    }

    /**
     * Loads real campus locations from PostgreSQL.
     */
    private List<String> loadLocations(int limit) throws Exception {

        List<String> locations = new ArrayList<>();

        ResultSet rs = db.executeQuery(
                "SELECT location_id, name " +
                "FROM locations " +
                "ORDER BY location_id " +
                "LIMIT " + limit
        );

        while (rs.next()) {
            String location =
                    rs.getInt("location_id") + " - " +
                    rs.getString("name");

            locations.add(location);
        }

        rs.close();

        return locations;
    }

    /**
     * Tests basic FIFO enqueue/dequeue behavior
     * using real PostgreSQL campus locations.
     */
    @Test
    void testEnqueueDequeue() throws Exception {

        List<String> locations = loadLocations(2);

        assertEquals(
                2,
                locations.size(),
                "Database should return at least 2 campus locations"
        );

        queue.enqueue(locations.get(0));
        queue.enqueue(locations.get(1));

        assertEquals(locations.get(0), queue.dequeue());
        assertEquals(locations.get(1), queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests circular wrap-around using real PostgreSQL data.
     *
     * Trace:
     *
     * Initial:
     * front = 0
     * rear  = -1
     *
     * Enqueue 5 locations:
     * rear: 0 -> 1 -> 2 -> 3 -> 4
     *
     * Dequeue 2:
     * front: 0 -> 1 -> 2
     *
     * Enqueue 2 more:
     * rear: 4 -> 0 -> 1
     *
     * This demonstrates rear wrapping around.
     */
    @Test
    void testWrapAround() throws Exception {

        List<String> locations = loadLocations(5);

        assertEquals(
                5,
                locations.size(),
                "Database should return 5 campus locations"
        );

        // Fill queue.
        for (String location : locations) {
            queue.enqueue(location);
        }

        assertTrue(queue.isFull());

        // Remove first two.
        assertEquals(locations.get(0), queue.dequeue());
        assertEquals(locations.get(1), queue.dequeue());

        // Queue now contains:
        // locations[2], locations[3], locations[4]

        // Add two more real campus locations.
        queue.enqueue(locations.get(0));
        queue.enqueue(locations.get(1));

        /*
         * Rear has wrapped:
         *
         * rear:
         * 0 -> 1
         *
         * Queue logically contains:
         *
         * locations[2]
         * locations[3]
         * locations[4]
         * locations[0]
         * locations[1]
         */

        assertEquals(locations.get(2), queue.dequeue());
        assertEquals(locations.get(3), queue.dequeue());
        assertEquals(locations.get(4), queue.dequeue());
        assertEquals(locations.get(0), queue.dequeue());
        assertEquals(locations.get(1), queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests the full queue condition.
     */
    @Test
    void testIsFull() throws Exception {

        List<String> locations = loadLocations(CAPACITY);

        assertEquals(
                CAPACITY,
                locations.size(),
                "Database should provide enough campus locations"
        );

        assertFalse(queue.isFull());

        for (String location : locations) {
            queue.enqueue(location);
        }

        assertTrue(
                queue.isFull(),
                "Queue should be full after " + CAPACITY + " enqueues"
        );

        queue.dequeue();

        assertFalse(
                queue.isFull(),
                "Queue should not be full after one dequeue"
        );
    }

    /**
     * Tests the empty queue condition.
     */
    @Test
    void testIsEmpty() throws Exception {

        assertTrue(
                queue.isEmpty(),
                "Newly created queue should be empty"
        );

        List<String> locations = loadLocations(1);

        assertEquals(
                1,
                locations.size(),
                "Database should return at least one campus location"
        );

        queue.enqueue(locations.get(0));

        assertFalse(
                queue.isEmpty(),
                "Queue should not be empty after enqueue"
        );

        queue.dequeue();

        assertTrue(
                queue.isEmpty(),
                "Queue should be empty after removing its only element"
        );
    }

    /**
     * Tests that enqueueing into a full queue throws
     * IllegalStateException.
     */
    @Test
    void testEnqueueWhenFull() throws Exception {

        List<String> locations = loadLocations(CAPACITY);

        assertEquals(
                CAPACITY,
                locations.size(),
                "Database should provide enough campus locations"
        );

        for (String location : locations) {
            queue.enqueue(location);
        }

        assertThrows(
                IllegalStateException.class,
                () -> queue.enqueue("Extra location"),
                "Enqueueing into a full queue should throw IllegalStateException"
        );
    }

    /**
     * Tests that dequeueing from an empty queue throws
     * NoSuchElementException.
     */
    @Test
    void testDequeueWhenEmpty() {

        assertThrows(
                NoSuchElementException.class,
                () -> queue.dequeue(),
                "Dequeueing from an empty queue should throw NoSuchElementException"
        );
    }

    /**
     * Tests front/rear index movement and wrap-around.
     */
    @Test
    void testFrontRearIndexes() throws Exception {

        List<String> locations = loadLocations(CAPACITY);

        assertEquals(
                CAPACITY,
                locations.size(),
                "Database should provide enough campus locations"
        );

        /*
         * Initial state.
         *
         * front = 0
         * rear  = -1
         */
        assertEquals(0, queue.getFrontIndex());
        assertEquals(-1, queue.getRearIndex());

        // First enqueue.
        queue.enqueue(locations.get(0));

        assertEquals(0, queue.getFrontIndex());
        assertEquals(0, queue.getRearIndex());

        // Second enqueue.
        queue.enqueue(locations.get(1));

        assertEquals(0, queue.getFrontIndex());
        assertEquals(1, queue.getRearIndex());

        // Dequeue first location.
        assertEquals(
                locations.get(0),
                queue.dequeue()
        );

        assertEquals(
                1,
                queue.getFrontIndex()
        );

        assertEquals(
                1,
                queue.getRearIndex()
        );

        /*
         * Add remaining locations.
         *
         * rear:
         * 1 -> 2 -> 3 -> 4 -> 0
         */
        queue.enqueue(locations.get(2));
        assertEquals(2, queue.getRearIndex());

        queue.enqueue(locations.get(3));
        assertEquals(3, queue.getRearIndex());

        queue.enqueue(locations.get(4));
        assertEquals(4, queue.getRearIndex());

        /*
         * Queue currently contains four elements:
         *
         * front = 1
         * rear  = 4
         *
         * Add one more element.
         *
         * rear wraps from 4 to 0.
         */
        queue.enqueue(locations.get(0));

        assertEquals(
                0,
                queue.getRearIndex(),
                "Rear should wrap from index 4 to index 0"
        );

        assertTrue(queue.isFull());

        // Remove one element.
        assertEquals(
                locations.get(1),
                queue.dequeue()
        );

        assertEquals(
                2,
                queue.getFrontIndex(),
                "Front should move from index 1 to index 2"
        );
    }

    /**
     * Tests peek using real PostgreSQL data.
     */
    @Test
    void testPeek() throws Exception {

        List<String> locations = loadLocations(2);

        assertEquals(2, locations.size());

        queue.enqueue(locations.get(0));
        queue.enqueue(locations.get(1));

        // Peek should return the first element.
        assertEquals(
                locations.get(0),
                queue.peek()
        );

        // Peek must not remove the element.
        assertEquals(2, queue.size());

        // The first element should still be returned by dequeue.
        assertEquals(
                locations.get(0),
                queue.dequeue()
        );
    }
}