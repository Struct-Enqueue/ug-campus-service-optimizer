package com.ug.campusops.algorithms;

/**
 * Sorting algorithms: selection sort, insertion sort, merge sort, and quicksort.
 * All implemented from scratch — do NOT use java.util.Arrays.sort().
 *
 * Feature 6 — Search & Sort Engine
 *
 * Required evidence:
 *   - Stability and in-place discussion for selection/insertion sort
 *   - Recurrence/decomposition notes for merge sort and quicksort
 *   - Trace tables for insertion sort and merge sort or quicksort
 *   - Performance comparison at 100, 500, 1000, 5000, 10000 requests
 */
public class SortAlgorithms {

    /**
     * Selection sort: find the smallest element and swap it to the front, repeat.
     * O(n²) time, in-place, NOT stable.
     *
     * @param array the array to sort in ascending order
     * @param <T>   must be Comparable
     */
    public static <T extends Comparable<T>> void selectionSort(T[] array) {
        // TODO: Feature 6 team — implement this
        throw new UnsupportedOperationException("SortAlgorithms.selectionSort() not yet implemented");
    }

    /**
     * Insertion sort: insert each element into its correct position in the sorted portion.
     * O(n²) time, in-place, STABLE.
     *
     * @param array the array to sort in ascending order
     * @param <T>   must be Comparable
     */
    public static <T extends Comparable<T>> void insertionSort(T[] array) {
        // TODO: Feature 6 team — implement this
        throw new UnsupportedOperationException("SortAlgorithms.insertionSort() not yet implemented");
    }

    /**
     * Merge sort: divide the array in half, sort each half, merge the sorted halves.
     * O(n log n) time, NOT in-place (uses extra array), STABLE.
     *
     * @param array the array to sort in ascending order
     * @param <T>   must be Comparable
     */
    public static <T extends Comparable<T>> void mergeSort(T[] array) {
        // TODO: Feature 6 team — implement this
        throw new UnsupportedOperationException("SortAlgorithms.mergeSort() not yet implemented");
    }

    /**
     * Quicksort: pick a pivot, partition elements into smaller/larger halves, recurse.
     * O(n log n) average, O(n²) worst case, in-place, NOT stable.
     *
     * @param array the array to sort in ascending order
     * @param <T>   must be Comparable
     */
    public static <T extends Comparable<T>> void quickSort(T[] array) {
        // TODO: Feature 6 team — implement this
        throw new UnsupportedOperationException("SortAlgorithms.quickSort() not yet implemented");
    }
}
