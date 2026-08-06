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
        // TODO: Feature 4 team — implement this (add at end, then bubble up / sift up)
        throw new UnsupportedOperationException("MyPriorityQueue.insert() not yet implemented");
    }

    /**
     * Removes and returns the minimum element (highest priority / most urgent).
     *
     * @return the minimum element
     * @throws NoSuchElementException if the queue is empty
     */
    public T extractMin() {
        // TODO: Feature 4 team — implement this (swap root with last, remove last, sift down)
        throw new UnsupportedOperationException("MyPriorityQueue.extractMin() not yet implemented");
    }

    /**
     * Removes and returns the maximum element.
     * Note: For a min-heap this requires a linear scan of leaf nodes.
     *
     * @return the maximum element
     * @throws NoSuchElementException if the queue is empty
     */
    public T extractMax() {
        // TODO: Feature 4 team — implement this
        throw new UnsupportedOperationException("MyPriorityQueue.extractMax() not yet implemented");
    }

    /**
     * Returns the minimum element without removing it.
     *
     * @return the minimum element
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        // TODO: Feature 4 team — implement this
        throw new UnsupportedOperationException("MyPriorityQueue.peek() not yet implemented");
    }

    /**
     * Builds a valid heap from the current internal array (bottom-up heap construction).
     * Called after bulk-loading elements.
     */
    public void heapify() {
        // TODO: Feature 4 team — implement this (start from last non-leaf, sift down each)
        throw new UnsupportedOperationException("MyPriorityQueue.heapify() not yet implemented");
    }

    /** Returns true if the queue contains no elements. */
    public boolean isEmpty() { return size == 0; }

    /** Returns the number of elements in the queue. */
    public int size() { return size; }

    // ── Helper methods (for the team to implement) ───────────────────────

    /** Moves an element up to restore heap order after insertion. */
    private void siftUp(int index) {
        // TODO: Feature 4 team — implement this
    }

    /** Moves an element down to restore heap order after removal. */
    private void siftDown(int index) {
        // TODO: Feature 4 team — implement this
    }

    /** Grows the internal array when capacity is exceeded. */
    private void resize() {
        // TODO: Feature 4 team — implement this
    }
}
