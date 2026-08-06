package com.ug.campusops.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic doubly-linked list. Each node points to both the next and previous node.
 * This is a custom implementation — do NOT use java.util.LinkedList.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence: diagram plus iterator demo.
 *
 * @param <T> the type of elements stored in this list
 */
public class MyLinkedList<T> implements Iterable<T> {

    /**
     * Internal node class. Each node holds data and references to the next and previous nodes.
     */
    public static class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /** Creates an empty linked list. */
    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Inserts an element at the front of the list.
     *
     * @param element the element to add
     */
    public void addFirst(T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyLinkedList.addFirst() not yet implemented");
    }

    /**
     * Inserts an element at the end of the list.
     *
     * @param element the element to add
     */
    public void addLast(T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyLinkedList.addLast() not yet implemented");
    }

    /**
     * Inserts a new element immediately after the specified node.
     *
     * @param node    the existing node to insert after
     * @param element the new element to insert
     * @throws IllegalArgumentException if node is null
     */
    public void insertAfter(Node<T> node, T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyLinkedList.insertAfter() not yet implemented");
    }

    /**
     * Removes the first occurrence of the specified element.
     *
     * @param element the element to remove
     * @return true if the element was found and removed, false otherwise
     */
    public boolean remove(T element) {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyLinkedList.remove() not yet implemented");
    }

    /**
     * Returns the first element without removing it.
     *
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    public T getFirst() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyLinkedList.getFirst() not yet implemented");
    }

    /**
     * Returns the last element without removing it.
     *
     * @return the last element
     * @throws NoSuchElementException if the list is empty
     */
    public T getLast() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyLinkedList.getLast() not yet implemented");
    }

    /** Returns the number of elements in the list. */
    public int size() { return size; }

    /** Returns true if the list is empty. */
    public boolean isEmpty() { return size == 0; }

    /** Returns the head node (for diagram/traversal demos). */
    public Node<T> getHead() { return head; }

    /** Returns the tail node (for diagram/traversal demos). */
    public Node<T> getTail() { return tail; }

    /**
     * Returns an iterator over the elements in this list (front to back).
     * Required for the iterator demo evidence.
     */
    @Override
    public Iterator<T> iterator() {
        // TODO: Feature 2 team — implement this
        throw new UnsupportedOperationException("MyLinkedList.iterator() not yet implemented");
    }
}
