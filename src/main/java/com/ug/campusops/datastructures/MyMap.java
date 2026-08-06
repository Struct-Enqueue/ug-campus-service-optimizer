package com.ug.campusops.datastructures;

/**
 * A generic map (key-value store) built on top of the custom HashTable.
 * Associates keys with values for fast lookup.
 *
 * This is a custom implementation — do NOT use java.util.HashMap or TreeMap.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: membership and lookup use case.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class MyMap<K, V> {

    // Internally backed by our custom HashTable
    private HashTable<K, V> table;

    /** Creates an empty map. */
    public MyMap() {
        this.table = new HashTable<>();
    }

    /** Creates an empty map with the specified hash table size. */
    public MyMap(int tableSize) {
        this.table = new HashTable<>(tableSize);
    }

    /**
     * Associates the specified value with the specified key.
     * If the key already exists, its value is updated.
     *
     * @param key   the key
     * @param value the value to associate
     */
    public void put(K key, V value) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MyMap.put() not yet implemented");
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key to look up
     * @return the associated value, or null if not found
     */
    public V get(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MyMap.get() not yet implemented");
    }

    /**
     * Removes the entry with the specified key.
     *
     * @param key the key to remove
     * @return the removed value, or null if not found
     */
    public V remove(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MyMap.remove() not yet implemented");
    }

    /**
     * Returns true if the map contains the specified key.
     *
     * @param key the key to check
     */
    public boolean containsKey(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MyMap.containsKey() not yet implemented");
    }

    /**
     * Returns a DynamicArray of all keys in the map.
     * (Returns DynamicArray since we can't use java.util.List)
     */
    public DynamicArray<K> keys() {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MyMap.keys() not yet implemented");
    }

    /**
     * Returns a DynamicArray of all values in the map.
     */
    public DynamicArray<V> values() {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("MyMap.values() not yet implemented");
    }

    /** Returns the number of key-value pairs. */
    public int size() {
        return table.size();
    }

    /** Returns true if the map is empty. */
    public boolean isEmpty() {
        return table.isEmpty();
    }
}
