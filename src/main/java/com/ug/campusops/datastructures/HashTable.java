package com.ug.campusops.datastructures;

/**
 * A generic hash table using separate chaining for collision handling.
 * Think of it like an index at the back of a book — you can jump straight
 * to the entry you need instead of scanning every page.
 *
 * This is a custom implementation — do NOT use java.util.HashMap or Hashtable.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: collision statistics for different load factors.
 *
 * NOTE: The initial table size should be derived from your team's index numbers
 *       (see Part B of the dataset document). Use a PRIME number for best distribution.
 *       Placeholder: 37 (replace with your team's calculated value).
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class HashTable<K, V> {

    /**
     * A node in the separate-chaining linked list at each bucket.
     */
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // TODO: Replace 37 with your team's index-number-derived prime table size
    private static final int DEFAULT_TABLE_SIZE = 37;

    private Entry<K, V>[] table;
    private int size;
    private int collisionCount;

    /** Creates a hash table with the default size. */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.table = new Entry[DEFAULT_TABLE_SIZE];
        this.size = 0;
        this.collisionCount = 0;
    }

    /** Creates a hash table with the specified size. */
    @SuppressWarnings("unchecked")
    public HashTable(int tableSize) {
        this.table = new Entry[tableSize];
        this.size = 0;
        this.collisionCount = 0;
    }

    /**
     * Inserts or updates a key-value pair.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V value) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        if (current != null) collisionCount++;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        table[index] = new Entry<>(key, value, table[index]);
        size++;
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key the key to look up
     * @return the associated value, or null if not found
     */
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Removes the entry with the given key.
     *
     * @param key the key to remove
     * @return the removed value, or null if not found
     */
    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;
        
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /**
     * Returns true if the table contains the specified key.
     *
     * @param key the key to check
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns a DynamicArray of all keys in the hash table.
     */
    public DynamicArray<K> keys() {
        DynamicArray<K> keysList = new DynamicArray<>(size);
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                keysList.add(current.key);
                current = current.next;
            }
        }
        return keysList;
    }

    /**
     * Returns a DynamicArray of all values in the hash table.
     */
    public DynamicArray<V> values() {
        DynamicArray<V> valuesList = new DynamicArray<>(size);
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                valuesList.add(current.value);
                current = current.next;
            }
        }
        return valuesList;
    }

    /** Returns the number of key-value pairs stored. */
    public int size() { return size; }

    /** Returns true if the table is empty. */
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns the total number of collisions that have occurred.
     * Required for the collision statistics evidence.
     */
    public int getCollisionCount() { return collisionCount; }

    /**
     * Returns the current load factor (size / table length).
     * Required for the load factor experiment.
     */
    public double getLoadFactor() {
        return (double) size / table.length;
    }

    /**
     * Returns the number of buckets (table length).
     */
    public int getTableSize() { return table.length; }

    /**
     * Computes the bucket index for a given key.
     * Uses Math.abs(key.hashCode() % tableLength) to map to a valid index.
     */
    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }
}
