package com.ug.campusops.datastructures;

import java.util.NoSuchElementException;

/**
 * A generic queue (FIFO — First In, First Out).
 * Think of it like a line at a bank: the first person in line is the first one served.
 * This is a custom implementation — do NOT use java.util.Queue or java.util.LinkedList.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence: trace showing front/rear pointer movement.
 *
 * @param <T> the type of elements stored in this queue
 */
public class MyQueue<T> {

    // TODO: Feature 2 team — choose internal storage (array or linked list)

    /** Creates an empty queue. */
    public MyQueue() {
        // TODO: Feature 2 team — implement this
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param element the element to enqueue
     */
    public void enqueue(T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyQueue.enqueue() not yet implemented");
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyQueue.dequeue() not yet implemented");
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyQueue.peek() not yet implemented");
    }

    /**
     * Returns true if the queue contains no elements.
     */
    public boolean isEmpty() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyQueue.isEmpty() not yet implemented");
    }

    /**
     * Returns the number of elements in the queue.
     */
    public int size() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyQueue.size() not yet implemented");
    }
}
