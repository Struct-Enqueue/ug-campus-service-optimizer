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

    /** Creates a dynamic array with the default initial capacity of 10. */
    public DynamicArray() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /** Creates a dynamic array with the specified initial capacity. */
    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative: " + initialCapacity);
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Inserts an element at the specified index, shifting subsequent elements right.
     * Triggers resize if the array is full.
     *
     * @param index   position to insert at (0 <= index <= size)
     * @param element the element to insert
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void insert(int index, T element) {
        // TODO: Feature 2 team — implement this

         if (index < 0 || index > size) {
         throw new IndexOutOfBoundsException(
             "Index: " + index + ", Size: " + size
         );
      }

        if (size == data.length) {
          resize();
        }

        for (int i = size; i > index; i--) {
         data[i] = data[i - 1];
      }

        data[index] = element;
        size++;
    }

    /**
     * Appends an element to the end of the array.
     *
     * @param element the element to add
     */
    public void add(T element) {
        // TODO: Feature 2 team — implement this
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
        // TODO: Feature 2 team — implement this
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
     * @param index   position to update (0 <= index < size)
     * @param element the new element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void set(int index, T element) {
        // TODO: Feature 2 team — implement this
        if (index < 0 || index >= size ) {
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Size: " + size
            );  
        }
         data[index] = element;
    }
      


    /**
     * Removes and returns the element at the specified index, shifting subsequent elements left.
     *
     * @param index position to remove (0 <= index < size)
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T remove(int index) {
        // TODO: Feature 2 team — implement this
       if (index < 0 || index >= size){
        throw new IndexOutOfBoundsException(
            "Index: " + index + ", Size: " + size
        );
       }

       T removedElement = (T) data[index];

       for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
         }

       data[size - 1] = null;
        size--;

            return removedElement;
    }

    /**
     * Returns the number of elements currently stored.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the array contains no elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Doubles the internal array capacity. Called automatically when the array is full.
     * This method should be traced in the report to show capacity growth.
     */
    private void resize() {
        // TODO: Feature 2 team — implement this (double the capacity)
        int oldCapacity = data.length;
int newCapacity = oldCapacity == 0 ? 1 : oldCapacity * 2;

Object[] newData = new Object[newCapacity];

for (int i = 0; i < size; i++) {
    newData[i] = data[i];
}

data = newData;

System.out.println(
    "DynamicArray resized: "
    + oldCapacity
    + " -> "
    + newCapacity
);
    }

    /**
     * Returns the current internal capacity (for testing/tracing the resize behavior).
     */
    public int getCapacity() {
        return data.length;
    }
}
