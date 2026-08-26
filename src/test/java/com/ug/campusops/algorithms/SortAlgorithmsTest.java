package com.ug.campusops.algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Tests for SearchAlgorithms and SortAlgorithms. */
class SortAlgorithmsTest {

    private static final Integer[] EXPECTED = { 1, 2, 3, 4, 5, 6 };

    // ── Search tests ─────────────────────────────────────────────────────
    @Test
    void testLinearSearchFound() {
        assertEquals(2, SearchAlgorithms.linearSearch(new Integer[] { 4, 8, 15, 16 }, 15));
    }

    @Test
    void testLinearSearchNotFound() {
        assertEquals(-1, SearchAlgorithms.linearSearch(new Integer[] { 4, 8, 15 }, 16));
    }

    @Test
    void testBinarySearchFound() {
        assertEquals(3, SearchAlgorithms.binarySearch(EXPECTED, 4));
    }

    @Test
    void testBinarySearchNotFound() {
        assertEquals(-1, SearchAlgorithms.binarySearch(EXPECTED, 9));
    }

    @Test
    void testBinarySearchUnsortedInput() {
        Integer[] unsorted = { 3, 1, 2 };
        assertEquals(0, SearchAlgorithms.linearSearch(unsorted, 3));
        assertEquals(-1, SearchAlgorithms.binarySearch(unsorted, 3));
    }

    @Test
    void testSearchRejectsNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> SearchAlgorithms.linearSearch(null, 1));
        assertThrows(IllegalArgumentException.class,
                () -> SearchAlgorithms.binarySearch(new Integer[] { 1 }, null));
    }

    // ── Sort tests ───────────────────────────────────────────────────────
    @Test
    void testSelectionSort() {
        assertSorted(SortAlgorithms::selectionSort);
    }

    @Test
    void testInsertionSort() {
        assertSorted(SortAlgorithms::insertionSort);
    }

    @Test
    void testMergeSort() {
        assertSorted(SortAlgorithms::mergeSort);
    }

    @Test
    void testQuickSort() {
        assertSorted(SortAlgorithms::quickSort);
    }

    // ── Edge cases ───────────────────────────────────────────────────────
    @Test
    void testSortEmptyArray() {
        assertAllAlgorithms(new Integer[0]);
    }

    @Test
    void testSortSingleElement() {
        assertAllAlgorithms(new Integer[] { 7 });
    }

    @Test
    void testSortAlreadySorted() {
        assertAllAlgorithms(new Integer[] { 1, 2, 3, 4 });
    }

    @Test
    void testSortReverseSorted() {
        assertAllAlgorithms(new Integer[] { 4, 3, 2, 1 });
    }

    @Test
    void testSortDuplicates() {
        assertAllAlgorithms(new Integer[] { 3, 1, 3, 2, 1 });
    }

    // ── Performance comparison (for experiment) ──────────────────────────
    @Test
    void testSortPerformanceComparison() {
        for (int size : new int[] { 100, 500, 1000, 5000, 10000 }) {
            Integer[] source = descending(size);
            long selection = time(source, SortAlgorithms::selectionSort);
            long insertion = time(source, SortAlgorithms::insertionSort);
            long merge = time(source, SortAlgorithms::mergeSort);
            long quick = time(source, SortAlgorithms::quickSort);
            System.out.printf("size=%d selection=%dns insertion=%dns merge=%dns quick=%dns%n",
                    size, selection, insertion, merge, quick);
        }
    }

    @Test
    void testSearchPerformanceComparison() {
        for (int size : new int[] { 100, 500, 1000, 5000, 10000 }) {
            Integer[] values = ascending(size);
            long linearStart = System.nanoTime();
            assertEquals(size - 1, SearchAlgorithms.linearSearch(values, size - 1));
            long linearTime = System.nanoTime() - linearStart;

            long binaryStart = System.nanoTime();
            assertEquals(size - 1, SearchAlgorithms.binarySearch(values, size - 1));
            long binaryTime = System.nanoTime() - binaryStart;
            System.out.printf("search size=%d linear=%dns binary=%dns%n",
                    size, linearTime, binaryTime);
        }
    }

    private void assertSorted(java.util.function.Consumer<Integer[]> algorithm) {
        Integer[] values = { 5, 2, 6, 1, 3, 4 };
        algorithm.accept(values);
        assertArrayEquals(EXPECTED, values);
    }

    private void assertAllAlgorithms(Integer[] input) {
        assertSortedInput(input, SortAlgorithms::selectionSort);
        assertSortedInput(input, SortAlgorithms::insertionSort);
        assertSortedInput(input, SortAlgorithms::mergeSort);
        assertSortedInput(input, SortAlgorithms::quickSort);
    }

    private void assertSortedInput(Integer[] input, java.util.function.Consumer<Integer[]> algorithm) {
        Integer[] values = input.clone();
        algorithm.accept(values);
        for (int index = 1; index < values.length; index++) {
            assertTrue(values[index - 1] <= values[index]);
        }
    }

    private long time(Integer[] source, java.util.function.Consumer<Integer[]> algorithm) {
        Integer[] values = source.clone();
        long start = System.nanoTime();
        algorithm.accept(values);
        long elapsed = System.nanoTime() - start;
        assertSortedInput(values, value -> {
        });
        return elapsed;
    }

    private Integer[] descending(int size) {
        Integer[] values = new Integer[size];
        for (int index = 0; index < size; index++)
            values[index] = size - index;
        return values;
    }

    private Integer[] ascending(int size) {
        Integer[] values = new Integer[size];
        for (int index = 0; index < size; index++)
            values[index] = index;
        return values;
    }
}
