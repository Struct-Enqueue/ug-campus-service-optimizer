package com.ug.campusops.algorithms;

/**
 * Search algorithms: linear search and binary search.
 *
 * Feature 6 — Search & Sort Engine
 *
 * Required evidence:
 *   - Binary search precondition stated and tested (array must be sorted)
 *   - Counterexample: binary search on unsorted input
 *   - Performance comparison: linear vs binary search at 100, 500, 1000, 5000, 10000 records
 */
public class SearchAlgorithms {

    /**
     * Linear search: checks items one by one until the target is found.
     * Works on any array (sorted or unsorted). O(n) time.
     *
     * @param array  the array to search
     * @param target the value to find
     * @param <T>    must be Comparable
     * @return the index of the target, or -1 if not found
     */
    public static <T extends Comparable<T>> int linearSearch(T[] array, T target) {
        // TODO: Feature 6 team — implement this
        throw new UnsupportedOperationException("SearchAlgorithms.linearSearch() not yet implemented");
    }

    /**
     * Binary search: efficient search on a SORTED array. O(log n) time.
     * PRECONDITION: the array MUST be sorted in ascending order.
     *
     * @param sortedArray the sorted array to search
     * @param target      the value to find
     * @param <T>         must be Comparable
     * @return the index of the target, or -1 if not found
     */
    public static <T extends Comparable<T>> int binarySearch(T[] sortedArray, T target) {
        // TODO: Feature 6 team — implement this
        throw new UnsupportedOperationException("SearchAlgorithms.binarySearch() not yet implemented");
    }
}
