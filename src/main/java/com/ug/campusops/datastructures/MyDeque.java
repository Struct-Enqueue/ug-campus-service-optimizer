package com.ug.campusops.datastructures;

import java.util.NoSuchElementException;

/**
 * A generic double-ended queue (deque) that supports insertion and removal from both ends.
 * Use case: urgent maintenance requests can be inserted at the front, while normal
 * requests go to the rear.
 * This is a custom implementation — do NOT use java.util.Deque or java.util.ArrayDeque.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence: urgent request insertion example.
 *
 * @param <T> the type of elements stored in this deque
 */
public class MyDeque<T> {

    // TODO: Feature 2 team — choose internal storage (circular array or doubly-linked list)

    /** Creates an empty deque. */
    public MyDeque() {
        // TODO: Feature 2 team — implement this
    }

    /**
     * Inserts an element at the front of the deque.
     * Use for urgent/priority insertion.
     *
     * @param element the element to add
     */
    public void addFront(T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.addFront() not yet implemented");
    }

    /**
     * Inserts an element at the rear of the deque.
     * Use for normal FIFO insertion.
     *
     * @param element the element to add
     */
    public void addRear(T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.addRear() not yet implemented");
    }

    /**
     * Removes and returns the element at the front of the deque.
     *
     * @return the front element
     * @throws NoSuchElementException if the deque is empty
     */
    public T removeFront() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.removeFront() not yet implemented");
    }

    /**
     * Removes and returns the element at the rear of the deque.
     *
     * @return the rear element
     * @throws NoSuchElementException if the deque is empty
     */
    public T removeRear() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.removeRear() not yet implemented");
    }

    /**
     * Returns the element at the front without removing it.
     *
     * @return the front element
     * @throws NoSuchElementException if the deque is empty
     */
    public T peekFront() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.peekFront() not yet implemented");
    }

    /**
     * Returns the element at the rear without removing it.
     *
     * @return the rear element
     * @throws NoSuchElementException if the deque is empty
     */
    public T peekRear() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.peekRear() not yet implemented");
    }

    /** Returns true if the deque contains no elements. */
    public boolean isEmpty() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.isEmpty() not yet implemented");
    }

    /** Returns the number of elements in the deque. */
    public int size() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyDeque.size() not yet implemented");
    }
}
