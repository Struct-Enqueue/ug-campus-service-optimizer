package com.ug.campusops.datastructures;

import java.util.NoSuchElementException;

/**
 * A generic min-heap-based priority queue. The element with the lowest priority value
 * (most urgent) is always at the front. Used for maintenance dispatch scheduling.
 *
 * This is a custom implementation — do NOT use java.util.PriorityQueue.
 *
 * Feature 4 — Priority Queue, Graph Core & Disjoint Set
 *
 * Required evidence: dispatch order trace showing extractMin ordering.
 *
 * AGREED METHOD NAMES (do not rename):
 *   insert(), extractMin(), extractMax(), peek(), heapify(), isEmpty(), size()
 *
 * @param <T> the type of elements stored; must be Comparable
 */
@SuppressWarnings("unchecked")
public class MyPriorityQueue<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 16;
    private Object[] heap;
    private int size;

    /** Creates an empty priority queue (min-heap) with default capacity. */
    public MyPriorityQueue() {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /** Creates an empty priority queue (min-heap) with the specified initial capacity. */
    public MyPriorityQueue(int initialCapacity) {
        this.heap = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Inserts an element into the priority queue and restores heap order.
     *
     * @param element the element to insert
     */
    public void insert(T element) {
        if (element == null) {
            throw new IllegalArgumentException("element cannot be null");
        }
        if (size == heap.length) {
            resize();
        }
        heap[size] = element;
        siftUp(size);
        size++;
    }

    /**
     * Removes and returns the minimum element (highest priority / most urgent).
     *
     * @return the minimum element
     * @throws NoSuchElementException if the queue is empty
     */
    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }

        T min = (T) heap[0];
        T last = (T) heap[size - 1];
        heap[0] = last;
        heap[size - 1] = null;
        size--;

        if (size > 0) {
            siftDown(0);
        }

        return min;
    }

    /**
     * Removes and returns the maximum element.
     * Note: For a min-heap this requires a linear scan of leaf nodes.
     *
     * @return the maximum element
     * @throws NoSuchElementException if the queue is empty
     */
    public T extractMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }

        int maxIndex = 0;
        for (int i = 1; i < size; i++) {
            if (((T) heap[i]).compareTo((T) heap[maxIndex]) > 0) {
                maxIndex = i;
            }
        }

        T max = (T) heap[maxIndex];
        T last = (T) heap[size - 1];
        heap[maxIndex] = last;
        heap[size - 1] = null;
        size--;

        if (maxIndex < size) {
            if (maxIndex > 0 && ((T) heap[maxIndex]).compareTo((T) heap[(maxIndex - 1) / 2]) < 0) {
                siftUp(maxIndex);
            } else {
                siftDown(maxIndex);
            }
        }

        return max;
    }

    /**
     * Returns the minimum element without removing it.
     *
     * @return the minimum element
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return (T) heap[0];
    }

    /**
     * Builds a valid heap from the current internal array (bottom-up heap construction).
     * Called after bulk-loading elements.
     */
    public void heapify() {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /** Returns true if the queue contains no elements. */
    public boolean isEmpty() { return size == 0; }

    /** Returns the number of elements in the queue. */
    public int size() { return size; }

    // ── Helper methods (for the team to implement) ───────────────────────

    /** Moves an element up to restore heap order after insertion. */
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T current = (T) heap[index];
            T parent = (T) heap[parentIndex];
            if (current.compareTo(parent) >= 0) {
                break;
            }
            heap[index] = parent;
            heap[parentIndex] = current;
            index = parentIndex;
        }
    }

    /** Moves an element down to restore heap order after removal. */
    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && ((T) heap[left]).compareTo((T) heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && ((T) heap[right]).compareTo((T) heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == index) {
                break;
            }

            Object temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;
            index = smallest;
        }
    }

    /** Grows the internal array when capacity is exceeded. */
    private void resize() {
        int newCapacity = heap.length == 0 ? DEFAULT_CAPACITY : heap.length * 2;
        Object[] newHeap = new Object[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }
}
