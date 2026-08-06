package com.ug.campusops.datastructures;

/**
 * A generic Binary Search Tree (BST). Organizes data in a tree shape so that
 * searching is O(log n) on average instead of O(n) with a linear scan.
 *
 * This is a custom implementation — do NOT use java.util.TreeMap or TreeSet.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: search path trace and sorted inorder output.
 *
 * @param <K> the type of keys (must be Comparable)
 * @param <V> the type of values
 */
public class BST<K extends Comparable<K>, V> {

    /**
     * Internal node of the BST.
     */
    protected static class BSTNode<K, V> {
        public K key;
        public V value;
        public BSTNode<K, V> left;
        public BSTNode<K, V> right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    protected BSTNode<K, V> root;
    protected int size;

    /** Creates an empty BST. */
    public BST() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair. If the key already exists, updates the value.
     *
     * @param key   the key to insert
     * @param value the value associated with the key
     */
    public void insert(K key, V value) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BST.insert() not yet implemented");
    }

    /**
     * Searches for a value by key.
     *
     * @param key the key to search for
     * @return the associated value, or null if not found
     */
    public V search(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BST.search() not yet implemented");
    }

    /**
     * Deletes a node with the specified key.
     *
     * @param key the key to delete
     * @return true if the key was found and deleted, false otherwise
     */
    public boolean delete(K key) {
        // TODO: Feature 3 team — implement this (handle 0, 1, and 2 children cases)
        throw new UnsupportedOperationException("BST.delete() not yet implemented");
    }

    /**
     * Returns all key-value pairs in sorted (inorder) order.
     * Left subtree → Root → Right subtree
     * Required for the "sorted inorder output" evidence.
     */
    public void inorderTraversal() {
        // TODO: Feature 3 team — implement this (print or collect to a list)
        throw new UnsupportedOperationException("BST.inorderTraversal() not yet implemented");
    }

    /**
     * Root → Left subtree → Right subtree
     */
    public void preorderTraversal() {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BST.preorderTraversal() not yet implemented");
    }

    /**
     * Left subtree → Right subtree → Root
     */
    public void postorderTraversal() {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BST.postorderTraversal() not yet implemented");
    }

    /** Returns the number of nodes in the tree. */
    public int size() { return size; }

    /** Returns true if the tree is empty. */
    public boolean isEmpty() { return root == null; }

    /** Returns the height of the tree (for performance comparison with balanced trees). */
    public int height() {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("BST.height() not yet implemented");
    }

    /** Returns the root node (for testing/diagramming). */
    public BSTNode<K, V> getRoot() { return root; }
}
