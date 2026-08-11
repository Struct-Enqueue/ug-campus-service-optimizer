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
        Node<T> newNode = new Node<>(element);

        if (head == null) {
            // List is empty
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }

        size++;
    }

    /**
     * Inserts an element at the end of the list.
     *
     * @param element the element to add
     */
    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);

        if (tail == null) {
            // List is empty
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    /**
     * Inserts a new element immediately after the specified node.
     *
     * @param node    the existing node to insert after
     * @param element the new element to insert
     * @throws IllegalArgumentException if node is null
     */
    public void insertAfter(Node<T> node, T element) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }

        Node<T> newNode = new Node<>(element);

        newNode.prev = node;
        newNode.next = node.next;

        if (node.next != null) {
            node.next.prev = newNode;
        } else {
            // Inserting after the tail
            tail = newNode;
        }

        node.next = newNode;

        size++;
    }

    /**
     * Removes the first occurrence of the specified element.
     *
     * @param element the element to remove
     * @return true if the element was found and removed, false otherwise
     */
    public boolean remove(T element) {
        Node<T> current = head;

        while (current != null) {

            if (element == null ? current.data == null : element.equals(current.data)) {

                // Removing the head
                if (current.prev == null) {
                    head = current.next;
                } else {
                    current.prev.next = current.next;
                }

                // Removing the tail
                if (current.next == null) {
                    tail = current.prev;
                } else {
                    current.next.prev = current.prev;
                }

                size--;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Returns the first element without removing it.
     *
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        return head.data;
    }

    /**
     * Returns the last element without removing it.
     *
     * @return the last element
     * @throws NoSuchElementException if the list is empty
     */
    public T getLast() {
        if (tail == null) {
            throw new NoSuchElementException("List is empty");
        }

        return tail.data;
    }

    /** Returns the number of elements in the list. */
    public int size() {
        return size;
    }

    /** Returns true if the list is empty. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the head node (for diagram/traversal demos). */
    public Node<T> getHead() {
        return head;
    }

    /** Returns the tail node (for diagram/traversal demos). */
    public Node<T> getTail() {
        return tail;
    }

    /**
     * Returns an iterator over the elements in this list (front to back).
     * Required for the iterator demo evidence.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T data = current.data;
                current = current.next;

                return data;
            }
        };
    }
}