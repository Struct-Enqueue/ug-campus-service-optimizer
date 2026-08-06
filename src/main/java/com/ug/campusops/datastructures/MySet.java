package com.ug.campusops.datastructures;

/**
 * A generic set built on top of the custom HashTable.
 * Stores unique elements only — duplicates are rejected.
 *
 * This is a custom implementation — do NOT use java.util.HashSet or TreeSet.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: membership and lookup use case.
 *
 * @param <T> the type of elements
 */
public class MySet<T> {

    // Internally backed by our custom HashTable (element → Boolean.TRUE as dummy value)
    private HashTable<T, Boolean> table;

    /** Creates an empty set. */
    public MySet() {
        this.table = new HashTable<>();
    }

    /** Creates an empty set with the specified hash table size. */
    public MySet(int tableSize) {
        this.table = new HashTable<>(tableSize);
    }

    /**
     * Adds an element to the set. Ignored if already present.
     *
     * @param element the element to add
     * @return true if the element was added (was not already present)
     */
    public boolean add(T element) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MySet.add() not yet implemented");
    }

    /**
     * Removes an element from the set.
     *
     * @param element the element to remove
     * @return true if the element was found and removed
     */
    public boolean remove(T element) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MySet.remove() not yet implemented");
    }

    /**
     * Returns true if the set contains the specified element.
     *
     * @param element the element to check
     */
    public boolean contains(T element) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MySet.contains() not yet implemented");
    }

    /** Returns the number of elements in the set. */
    public int size() {
        return table.size();
    }

    /** Returns true if the set is empty. */
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Returns a new set containing elements present in BOTH this set and the other set.
     *
     * @param otherSet the set to intersect with
     * @return the intersection set
     */
    public MySet<T> intersection(MySet<T> otherSet) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MySet.intersection() not yet implemented");
    }

    /**
     * Returns a new set containing all elements from both this set and the other set.
     *
     * @param otherSet the set to union with
     * @return the union set
     */
    public MySet<T> union(MySet<T> otherSet) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MySet.union() not yet implemented");
    }
}
