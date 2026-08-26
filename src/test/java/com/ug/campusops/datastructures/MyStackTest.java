package com.ug.campusops.datastructures;

import com.ug.campusops.db.DatabaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MyStack.
 *
 * Uses real campus location records from PostgreSQL through
 * DatabaseConnector instead of hardcoded test data.
 *
 * Required:
 * - Normal case
 * - Boundary case
 * - Invalid input case
 * - LIFO behavior
 * - Undo log demonstration
 */
class MyStackTest {

    private MyStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    /**
     * Fetches real location names from the PostgreSQL database.
     */
    private List<String> loadLocations(int limit) throws Exception {

        DatabaseConnector db = new DatabaseConnector();
        List<String> locations = new ArrayList<>();

        try {
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

        } finally {
            db.closeConnection();
        }

        return locations;
    }

    // ---------------------------------------------------------------
    // Normal cases
    // ---------------------------------------------------------------

    @Test
    void testPushAndPopWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(2);

        assertEquals(2, locations.size());

        stack.push(locations.get(0));
        stack.push(locations.get(1));

        assertEquals(locations.get(1), stack.pop());
        assertEquals(locations.get(0), stack.pop());
    }

    @Test
    void testPeekWithDatabaseRecord() throws Exception {

        List<String> locations = loadLocations(2);

        stack.push(locations.get(0));
        stack.push(locations.get(1));

        assertEquals(locations.get(1), stack.peek());

        // Peek must not remove the element.
        assertEquals(2, stack.size());
        assertEquals(locations.get(1), stack.peek());
    }

    @Test
    void testLIFOOrderWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(3);

        assertEquals(3, locations.size());

        stack.push(locations.get(0));
        stack.push(locations.get(1));
        stack.push(locations.get(2));

        // Last record pushed must come out first.
        assertEquals(locations.get(2), stack.pop());
        assertEquals(locations.get(1), stack.pop());
        assertEquals(locations.get(0), stack.pop());
    }

    @Test
    void testIsEmpty() throws Exception {

        assertTrue(stack.isEmpty());

        List<String> locations = loadLocations(1);

        stack.push(locations.get(0));

        assertFalse(stack.isEmpty());

        stack.pop();

        assertTrue(stack.isEmpty());
    }

    // ---------------------------------------------------------------
    // Boundary case
    // ---------------------------------------------------------------

    @Test
    void testSingleDatabaseRecord() throws Exception {

        List<String> locations = loadLocations(1);

        assertEquals(1, locations.size());

        stack.push(locations.get(0));

        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(locations.get(0), stack.peek());

        assertEquals(locations.get(0), stack.pop());

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // ---------------------------------------------------------------
    // Invalid input cases
    // ---------------------------------------------------------------

    @Test
    void testPopEmpty() {

        assertThrows(
                NoSuchElementException.class,
                () -> stack.pop()
        );
    }

    @Test
    void testPeekEmpty() {

        assertThrows(
                NoSuchElementException.class,
                () -> stack.peek()
        );
    }

    // ---------------------------------------------------------------
    // Undo log demonstration
    // ---------------------------------------------------------------

    @Test
    void testUndoLogUsingDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(3);

        assertEquals(3, locations.size());

        /*
         * Simulate an undo log:
         *
         * The application processes three real campus locations.
         * Each processed location is pushed onto the stack.
         *
         * Because the stack is LIFO, the most recently processed
         * location is the first one that can be undone.
         */

        stack.push(locations.get(0));
        stack.push(locations.get(1));
        stack.push(locations.get(2));

        assertEquals(3, stack.size());

        // Undo the most recent operation.
        String undoneOperation = stack.pop();

        assertEquals(locations.get(2), undoneOperation);
        assertEquals(2, stack.size());

        // Undo the next operation.
        undoneOperation = stack.pop();

        assertEquals(locations.get(1), undoneOperation);
        assertEquals(1, stack.size());

        // Undo the first operation.
        undoneOperation = stack.pop();

        assertEquals(locations.get(0), undoneOperation);
        assertTrue(stack.isEmpty());
    }
}