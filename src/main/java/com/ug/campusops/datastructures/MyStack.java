package com.ug.campusops.datastructures;

import java.util.NoSuchElementException;

/**
 * A generic stack (LIFO — Last In, First Out).
 *
 * Think of it like a stack of plates:
 * the last plate placed on top is the first one removed.
 *
 * This is a custom implementation — do NOT use
 * java.util.Stack or java.util.ArrayDeque.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence: undo log or recursion simulation trace.
 *
 * @param <T> the type of elements stored in this stack
 */
public class MyStack<T> {

    // Internal storage for stack elements
    private Object[] elements;

    // Number of elements currently in the stack
    private int size;

    // Initial capacity of the stack
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Creates an empty stack.
     */
    public MyStack() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param element the element to push
     */
    public void push(T element) {

        // Resize the array if it is full
        if (size == elements.length) {
            resize();
        }

        // Add the element at the top
        elements[size] = element;

        // Increase the number of elements
        size++;
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    @SuppressWarnings("unchecked")
    public T pop() {

        // A stack cannot be popped when empty
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Cannot pop from an empty stack"
            );
        }

        // Get the top element
        T element = (T) elements[size - 1];

        // Remove the reference from the array
        elements[size - 1] = null;

        // Decrease the size
        size--;

        return element;
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    @SuppressWarnings("unchecked")
    public T peek() {

        // A stack cannot be peeked when empty
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Cannot peek at an empty stack"
            );
        }

        return (T) elements[size - 1];
    }

    /**
     * Returns true if the stack contains no elements.
     *
     * @return true if the stack is empty, otherwise false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Doubles the capacity of the internal array.
     */
    private void resize() {

        Object[] newElements = new Object[elements.length * 2];

        // Copy existing elements into the new array
        System.arraycopy(
                elements,
                0,
                newElements,
                0,
                elements.length
        );

        elements = newElements;
    }
}