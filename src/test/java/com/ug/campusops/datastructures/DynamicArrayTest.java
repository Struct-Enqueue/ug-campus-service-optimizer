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
        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void testInsertAtIndex() {
        // TODO: insert at beginning, middle, end
         array.add(10);
         array.add(30);

        // Insert at beginning
        array.insert(0, 5);

        // Insert in middle
        array.insert(2, 20);

        // Insert at end
        array.insert(4, 40);

        assertEquals(5, array.get(0));
        assertEquals(10, array.get(1));
        assertEquals(20, array.get(2));
        assertEquals(30, array.get(3));
        assertEquals(40, array.get(4));
    }

    @Test
    void testRemove() {
        // TODO: remove element and verify shift
         array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());

        // Verify that elements shifted left
        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
    }

    
    @Test
    void testSet() {
        // TODO: update element at index
        array.add(10);
        array.add(20);
        array.add(30);

        array.set(1, 99);

        assertEquals(10, array.get(0));
        assertEquals(99, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void testSize() {
        // TODO: verify size after add/remove
        assertEquals(0, array.size());

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(3, array.size());

        array.remove(1);

        assertEquals(2, array.size());
    }

    // ── Boundary cases ───────────────────────────────────────────────────

    @Test
    void testEmptyArray() {
        // TODO: verify isEmpty, size=0 on new array
         assertTrue(array.isEmpty());
        assertEquals(0, array.size());
    }

    @Test
    void testSingleElement() {
        // TODO: add one, get it, remove it
         array.add(100);

        assertEquals(1, array.size());
        assertFalse(array.isEmpty());
        assertEquals(100, array.get(0));

        int removed = array.remove(0);

        assertEquals(100, removed);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
    }

    @Test
    void testResizeTrace() {
        // TODO: add elements beyond initial capacity, verify capacity doubles
        // This produces the resize trace required by the brief
         // Default capacity is 10
        assertEquals(10, array.getCapacity());

        // Fill the array to capacity
        for (int i = 0; i < 10; i++) {
            array.add(i);
        }

        assertEquals(10, array.size());
        assertEquals(10, array.getCapacity());

        // Adding the 11th element should resize:
        // 10 -> 20
        array.add(10);

        assertEquals(11, array.size());
        assertEquals(20, array.getCapacity());

        // Add enough elements to trigger another resize:
        // 20 -> 40
        for (int i = 11; i < 21; i++) {
            array.add(i);
        }

        assertEquals(21, array.size());
        assertEquals(40, array.getCapacity());
    }

    // ── Invalid input cases ──────────────────────────────────────────────

    @Test
    void testGetOutOfBounds() {
        // TODO: verify IndexOutOfBoundsException
         array.add(10);

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
    void testRemoveOutOfBounds() {
        // TODO: verify IndexOutOfBoundsException
            array.add(10);

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
        // TODO: verify IllegalArgumentException for DynamicArray(-1)
        assertThrows(
            IllegalArgumentException.class,
            () -> new DynamicArray<Integer>(-1)
        );

    }
}
