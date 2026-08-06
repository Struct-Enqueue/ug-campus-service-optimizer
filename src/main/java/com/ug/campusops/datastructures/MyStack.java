package com.ug.campusops.datastructures;

import java.util.NoSuchElementException;

/**
 * A generic stack (LIFO — Last In, First Out).
 * Think of it like a stack of plates: the last plate placed on top is the first one removed.
 * This is a custom implementation — do NOT use java.util.Stack or java.util.ArrayDeque.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence: undo log or recursion simulation trace.
 *
 * @param <T> the type of elements stored in this stack
 */
public class MyStack<T> {

    // TODO: Feature 2 team — choose internal storage (array or linked list)

    /** Creates an empty stack. */
    public MyStack() {
        // TODO: Feature 2 team — implement this
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param element the element to push
     */
    public void push(T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyStack.push() not yet implemented");
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    public T pop() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyStack.pop() not yet implemented");
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    public T peek() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyStack.peek() not yet implemented");
    }

    /**
     * Returns true if the stack contains no elements.
     */
    public boolean isEmpty() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyStack.isEmpty() not yet implemented");
    }

    /**
     * Returns the number of elements in the stack.
     */
    public int size() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyStack.size() not yet implemented");
    }
}
