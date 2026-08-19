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

    /**
     * Internal node class.
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    /** Creates an empty deque. */
    public MyDeque() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Inserts an element at the front of the deque.
     *
     * @param element the element to add
     */
    public void addFront(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }

        size++;
    }

    /**
     * Inserts an element at the rear of the deque.
     *
     * @param element the element to add
     */
    public void addRear(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            newNode.prev = rear;
            rear.next = newNode;
            rear = newNode;
        }

        size++;
    }

    /**
     * Removes and returns the element at the front of the deque.
     *
     * @return the front element
     * @throws NoSuchElementException if the deque is empty
     */
    public T removeFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T element = front.data;

        if (size == 1) {
            front = null;
            rear = null;
        } else {
            front = front.next;
            front.prev = null;
        }

        size--;

        return element;
    }

    /**
     * Removes and returns the element at the rear of the deque.
     *
     * @return the rear element
     * @throws NoSuchElementException if the deque is empty
     */
    public T removeRear() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T element = rear.data;

        if (size == 1) {
            front = null;
            rear = null;
        } else {
            rear = rear.prev;
            rear.next = null;
        }

        size--;

        return element;
    }

    /**
     * Returns the element at the front without removing it.
     *
     * @return the front element
     * @throws NoSuchElementException if the deque is empty
     */
    public T peekFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        return front.data;
    }

    /**
     * Returns the element at the rear without removing it.
     *
     * @return the rear element
     * @throws NoSuchElementException if the deque is empty
     */
    public T peekRear() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        return rear.data;
    }

    /** Returns true if the deque contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of elements in the deque. */
    public int size() {
        return size;
    }
}