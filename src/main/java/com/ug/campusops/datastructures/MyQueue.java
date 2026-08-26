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

    /**
     * Internal node class.
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    /** Creates an empty queue. */
    public MyQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param element the element to enqueue
     */
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        T element = front.data;

        front = front.next;
        size--;

        // If the queue becomes empty,
        // rear must also become null.
        if (size == 0) {
            rear = null;
        }

        return element;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        return front.data;
    }

    /**
     * Returns true if the queue contains no elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the queue.
     */
    public int size() {
        return size;
    }
}