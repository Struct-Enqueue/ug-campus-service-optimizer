package com.ug.campusops.datastructures;

/**
 * A generic dynamic array (array-backed list) that grows automatically when
 * it runs out of space. This is a custom implementation — do NOT use
 * java.util.ArrayList or similar built-in classes.
 *
 * Feature 2 — Linear Structures
 *
 * Required evidence: unit tests and resize trace showing capacity doubling.
 *
 * @param <T> the type of elements stored in this array
 */
@SuppressWarnings("unchecked")
public class DynamicArray<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] data;
    private int size;

    /**
     * Creates a dynamic array with the default initial capacity of 10.
     */
    public DynamicArray() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a dynamic array with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the array
     * @throws IllegalArgumentException if the capacity is negative
     */
    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "Capacity cannot be negative: " + initialCapacity
            );
        }

        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Inserts an element at the specified index, shifting subsequent
     * elements to the right.
     *
     * Triggers a resize if the array is full.
     *
     * @param index position to insert at (0 <= index <= size)
     * @param element the element to insert
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void insert(int index, T element) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }

        // Resize the array if it is full.
        if (size == data.length) {
            resize();
        }

        // Shift elements to the right.
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        // Insert the new element.
        data[index] = element;

        size++;
    }

    /**
     * Appends an element to the end of the array.
     *
     * @param element the element to add
     */
    public void add(T element) {
        insert(size, element);
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index position to retrieve (0 <= index < size)
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }

        return (T) data[index];
    }

    /**
     * Replaces the element at the specified index.
     *
     * @param index position to update (0 <= index < size)
     * @param element the new element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void set(int index, T element) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }

        data[index] = element;
    }

    /**
     * Removes and returns the element at the specified index,
     * shifting subsequent elements to the left.
     *
     * @param index position to remove (0 <= index < size)
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T remove(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }

        T removedElement = (T) data[index];

        // Shift elements to the left.
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        // Remove the duplicate reference at the end.
        data[size - 1] = null;

        size--;

        return removedElement;
    }

    /**
     * Returns the number of elements currently stored.
     *
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the array contains no elements.
     *
     * @return true if empty, otherwise false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Doubles the internal array capacity.
     *
     * Called automatically when the array is full.
     * This method also prints a resize trace for demonstration.
     */
    private void resize() {

        int oldCapacity = data.length;

        int newCapacity = oldCapacity == 0
                ? 1
                : oldCapacity * 2;

        Object[] newData = new Object[newCapacity];

        // Copy existing elements into the new array.
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        // Replace the old array.
        data = newData;

        // Resize trace for the project report.
        System.out.println(
                "DynamicArray resized: "
                        + oldCapacity
                        + " -> "
                        + newCapacity
        );
    }

    /**
     * Returns the current internal capacity.
     *
     * This method is useful for testing and tracing resize behavior.
     *
     * @return current capacity
     */
    public int getCapacity() {
        return data.length;
    }
}