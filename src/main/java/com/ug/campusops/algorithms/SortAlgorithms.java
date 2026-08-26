package com.ug.campusops.algorithms;

/**
 * Sorting algorithms: selection sort, insertion sort, merge sort, and
 * quicksort.
 * All implemented from scratch — do NOT use java.util.Arrays.sort().
 *
 * Feature 6 — Search & Sort Engine
 *
 * Required evidence:
 * - Stability and in-place discussion for selection/insertion sort
 * - Recurrence/decomposition notes for merge sort and quicksort
 * - Trace tables for insertion sort and merge sort or quicksort
 * - Performance comparison at 100, 500, 1000, 5000, 10000 requests
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
        requireArray(array);
        for (int start = 0; start < array.length - 1; start++) {
            int smallest = start;
            for (int index = start + 1; index < array.length; index++) {
                if (array[index].compareTo(array[smallest]) < 0)
                    smallest = index;
            }
            swap(array, start, smallest);
        }
    }

    /**
     * Insertion sort: insert each element into its correct position in the sorted
     * portion.
     * O(n²) time, in-place, STABLE.
     *
     * @param array the array to sort in ascending order
     * @param <T>   must be Comparable
     */
    public static <T extends Comparable<T>> void insertionSort(T[] array) {
        requireArray(array);
        for (int index = 1; index < array.length; index++) {
            T value = array[index];
            int position = index - 1;
            while (position >= 0 && array[position].compareTo(value) > 0) {
                array[position + 1] = array[position];
                position--;
            }
            array[position + 1] = value;
        }
    }

    /**
     * Merge sort: divide the array in half, sort each half, merge the sorted
     * halves.
     * O(n log n) time, NOT in-place (uses extra array), STABLE.
     *
     * @param array the array to sort in ascending order
     * @param <T>   must be Comparable
     */
    public static <T extends Comparable<T>> void mergeSort(T[] array) {
        requireArray(array);
        if (array.length < 2)
            return;
        mergeSort(array, 0, array.length - 1);
    }

    /**
     * Quicksort: pick a pivot, partition elements into smaller/larger halves,
     * recurse.
     * O(n log n) average, O(n²) worst case, in-place, NOT stable.
     *
     * @param array the array to sort in ascending order
     * @param <T>   must be Comparable
     */
    public static <T extends Comparable<T>> void quickSort(T[] array) {
        requireArray(array);
        quickSort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void mergeSort(T[] array, int low, int high) {
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        mergeSort(array, low, middle);
        mergeSort(array, middle + 1, high);
        merge(array, low, middle, high);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge(T[] array, int low, int middle, int high) {
        Object[] merged = new Object[high - low + 1];
        int left = low;
        int right = middle + 1;
        int output = 0;
        while (left <= middle && right <= high) {
            if (array[left].compareTo(array[right]) <= 0)
                merged[output++] = array[left++];
            else
                merged[output++] = array[right++];
        }
        while (left <= middle)
            merged[output++] = array[left++];
        while (right <= high)
            merged[output++] = array[right++];
        for (int index = 0; index < merged.length; index++) {
            array[low + index] = (T) merged[index];
        }
    }

    private static <T extends Comparable<T>> void quickSort(T[] array, int low, int high) {
        if (low >= high)
            return;
        int left = low;
        int right = high;
        T pivot = array[low + (high - low) / 2];
        while (left <= right) {
            while (array[left].compareTo(pivot) < 0)
                left++;
            while (array[right].compareTo(pivot) > 0)
                right--;
            if (left <= right)
                swap(array, left++, right--);
        }
        if (low < right)
            quickSort(array, low, right);
        if (left < high)
            quickSort(array, left, high);
    }

    private static <T> void swap(T[] array, int first, int second) {
        T value = array[first];
        array[first] = array[second];
        array[second] = value;
    }

    private static <T> void requireArray(T[] array) {
        if (array == null)
            throw new IllegalArgumentException("array must not be null");
    }
}
