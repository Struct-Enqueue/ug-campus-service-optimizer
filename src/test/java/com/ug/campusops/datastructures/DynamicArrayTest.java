package com.ug.campusops.datastructures;

import com.ug.campusops.db.DatabaseConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DynamicArray.
 *
 * Uses real campus location records from PostgreSQL through
 * DatabaseConnector.
 *
 * Required:
 * - Normal case
 * - Boundary case
 * - Invalid input case
 * - Resize trace
 */
class DynamicArrayTest {

    private DynamicArray<String> array;

    @BeforeEach
    void setUp() {
        array = new DynamicArray<>();
    }

    /**
     * Fetches real campus locations from PostgreSQL.
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

    // ── Normal cases ────────────────────────────────────────────────

    @Test
    void testAddAndGetWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(3);

        assertEquals(3, locations.size());

        array.add(locations.get(0));
        array.add(locations.get(1));
        array.add(locations.get(2));

        assertEquals(locations.get(0), array.get(0));
        assertEquals(locations.get(1), array.get(1));
        assertEquals(locations.get(2), array.get(2));
    }

    @Test
    void testInsertAtIndexWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(5);

        assertEquals(5, locations.size());

        array.add(locations.get(0));
        array.add(locations.get(2));

        // Insert at beginning.
        array.insert(0, locations.get(1));

        // Insert in the middle.
        array.insert(2, locations.get(3));

        // Insert at end.
        array.insert(4, locations.get(4));

        assertEquals(locations.get(1), array.get(0));
        assertEquals(locations.get(0), array.get(1));
        assertEquals(locations.get(3), array.get(2));
        assertEquals(locations.get(2), array.get(3));
        assertEquals(locations.get(4), array.get(4));
    }

    @Test
    void testRemoveWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(3);

        assertEquals(3, locations.size());

        array.add(locations.get(0));
        array.add(locations.get(1));
        array.add(locations.get(2));

        String removed = array.remove(1);

        assertEquals(locations.get(1), removed);
        assertEquals(2, array.size());

        // Verify elements shifted left.
        assertEquals(locations.get(0), array.get(0));
        assertEquals(locations.get(2), array.get(1));
    }

    @Test
    void testSetWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(3);

        assertEquals(3, locations.size());

        array.add(locations.get(0));
        array.add(locations.get(1));
        array.add(locations.get(2));

        array.set(1, locations.get(2));

        assertEquals(locations.get(0), array.get(0));
        assertEquals(locations.get(2), array.get(1));
        assertEquals(locations.get(2), array.get(2));
    }

    @Test
    void testSizeWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(3);

        assertEquals(3, locations.size());

        assertEquals(0, array.size());

        array.add(locations.get(0));
        array.add(locations.get(1));
        array.add(locations.get(2));

        assertEquals(3, array.size());

        array.remove(1);

        assertEquals(2, array.size());
    }

    // ── Boundary cases ──────────────────────────────────────────────

    @Test
    void testEmptyArray() {

        assertTrue(array.isEmpty());
        assertEquals(0, array.size());
    }

    @Test
    void testSingleDatabaseRecord() throws Exception {

        List<String> locations = loadLocations(1);

        assertEquals(1, locations.size());

        array.add(locations.get(0));

        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertEquals(locations.get(0), array.get(0));

        String removed = array.remove(0);

        assertEquals(locations.get(0), removed);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
    }

    /**
     * Tests automatic capacity doubling using real database records.
     *
     * The database contains 60 locations, so there are enough real
     * records to trigger the required 10 -> 20 -> 40 resizing.
     */
    @Test
    void testResizeTraceWithDatabaseRecords() throws Exception {

        List<String> locations = loadLocations(21);

        assertEquals(21, locations.size());

        // Initial capacity.
        assertEquals(10, array.getCapacity());

        // Add the first 10 real database records.
        for (int i = 0; i < 10; i++) {
            array.add(locations.get(i));
        }

        assertEquals(10, array.size());
        assertEquals(10, array.getCapacity());

        // 11th record should trigger 10 -> 20.
        array.add(locations.get(10));

        assertEquals(11, array.size());
        assertEquals(20, array.getCapacity());

        // Add records 12 through 20.
        for (int i = 11; i < 21; i++) {
            array.add(locations.get(i));
        }

        assertEquals(21, array.size());

        // 21 records still fit inside capacity 40.
        assertEquals(40, array.getCapacity());

        // Verify that actual database records survived resizing.
        assertEquals(locations.get(0), array.get(0));
        assertEquals(locations.get(10), array.get(10));
        assertEquals(locations.get(20), array.get(20));
    }

    // ── Invalid input cases ─────────────────────────────────────────

    @Test
    void testGetOutOfBounds() throws Exception {

        List<String> locations = loadLocations(1);

        array.add(locations.get(0));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(-1)
        );
    }

    @Test
    void testRemoveOutOfBounds() throws Exception {

        List<String> locations = loadLocations(1);

        array.add(locations.get(0));

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(-1)
        );
    }

    @Test
    void testNegativeCapacity() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new DynamicArray<Integer>(-1)
        );
    }
}