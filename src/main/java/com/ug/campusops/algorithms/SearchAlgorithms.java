package com.ug.campusops.algorithms;

/**
 * Search algorithms: linear search and binary search.
 *
 * Feature 6 — Search & Sort Engine
 *
 * Required evidence:
 * - Binary search precondition stated and tested (array must be sorted)
 * - Counterexample: binary search on unsorted input
 * - Performance comparison: linear vs binary search at 100, 500, 1000, 5000,
 * 10000 records
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
        requireInput(array, target);
        for (int index = 0; index < array.length; index++) {
            if (array[index].compareTo(target) == 0)
                return index;
        }
        return -1;
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
        requireInput(sortedArray, target);
        int low = 0;
        int high = sortedArray.length - 1;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int comparison = sortedArray[middle].compareTo(target);
            if (comparison == 0)
                return middle;
            if (comparison < 0)
                low = middle + 1;
            else
                high = middle - 1;
        }
        return -1;
    }

    private static <T> void requireInput(T[] array, T target) {
        if (array == null || target == null) {
            throw new IllegalArgumentException("array and target must not be null");
        }
    }
}
