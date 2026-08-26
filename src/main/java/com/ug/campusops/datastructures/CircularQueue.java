package com.ug.campusops.datastructures;
import java.util.NoSuchElementException;

/**
 * A generic circular queue backed by a fixed-size array.
 * When the rear pointer reaches the end, it wraps back to the beginning of the array,
 * reusing empty spaces left by dequeued elements instead of wasting memory.
 * This is a custom implementation — do NOT use java.util.ArrayDeque.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence: trace showing front/rear wrap-around movement.
 *
 * @param <T> the type of elements stored in this queue
 */
@SuppressWarnings("unchecked")
public class CircularQueue<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    /** Creates a circular queue with the default capacity of 10. */
    public CircularQueue() {
        this(DEFAULT_CAPACITY);
    }

    /** Creates a circular queue with the specified capacity. */
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Adds an element to the rear of the queue, wrapping around if necessary.
     *
     * @param element the element to enqueue
     * @throws IllegalStateException if the queue is full
     */
    public void enqueue(T element) {
        // TODO: Feature 2 team — implement this (remember to wrap rear)
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        data[rear] = element;
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue, wrapping around if necessary.
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        // TODO: Feature 2 team — implement this (remember to wrap front)
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        @SuppressWarnings("unchecked")
        T element = (T) data[front];
        front = (front + 1) % capacity;
        size--;
        return element;
    }

    /**
     * Returns the element at the front without removing it.
     *
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        // TODO: Feature 2 team — implement this
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        @SuppressWarnings("unchecked")
        T element = (T) data[front];
        return element;
    }

    /** Returns true if the queue contains no elements. */
    public boolean isEmpty() { return size == 0; }

    /** Returns true if the queue is at full capacity. */
    public boolean isFull() { return size == capacity; }

    /** Returns the number of elements in the queue. */
    public int size() { return size; }

    /** Returns the current front index (for tracing/debugging). */
    public int getFrontIndex() { return front; }

    /** Returns the current rear index (for tracing/debugging). */
    public int getRearIndex() { return rear; }
}
