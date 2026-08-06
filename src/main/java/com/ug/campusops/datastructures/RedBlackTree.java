package com.ug.campusops.datastructures;

/**
 * A Red-Black Tree: a self-balancing BST that guarantees O(log n) search, insert, and delete.
 * Each node is coloured RED or BLACK, and the tree maintains balance through rotations
 * and recolouring after every insertion or deletion.
 *
 * This is a custom implementation — do NOT use java.util.TreeMap.
 *
 * Feature 3 — Trees & Hashing
 *
 * Required evidence: before/after rotation diagrams and height discussion.
 *
 * @param <K> the type of keys (must be Comparable)
 * @param <V> the type of values
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    protected static final boolean RED   = true;
    protected static final boolean BLACK = false;

    /**
     * Internal node with a colour field.
     */
    protected static class RBNode<K, V> {
        public K key;
        public V value;
        public RBNode<K, V> left;
        public RBNode<K, V> right;
        public RBNode<K, V> parent;
        public boolean colour; // RED = true, BLACK = false

        public RBNode(K key, V value, boolean colour) {
            this.key = key;
            this.value = value;
            this.colour = colour;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    protected RBNode<K, V> root;
    protected int size;

    /** Creates an empty Red-Black Tree. */
    public RedBlackTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair and restores Red-Black properties via rotations/recolouring.
     *
     * @param key   the key to insert
     * @param value the associated value
     */
    public void insert(K key, V value) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("RedBlackTree.insert() not yet implemented");
    }

    /**
     * Searches for a value by key.
     *
     * @param key the key to search for
     * @return the associated value, or null if not found
     */
    public V search(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("RedBlackTree.search() not yet implemented");
    }

    /**
     * Deletes a node with the specified key and rebalances.
     *
     * @param key the key to delete
     * @return true if found and deleted, false otherwise
     */
    public boolean delete(K key) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("RedBlackTree.delete() not yet implemented");
    }

    /**
     * Left-rotates the subtree rooted at the given node.
     * Required for the "rotation diagrams" evidence.
     *
     * @param node the node to rotate around
     */
    protected void rotateLeft(RBNode<K, V> node) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("RedBlackTree.rotateLeft() not yet implemented");
    }

    /**
     * Right-rotates the subtree rooted at the given node.
     *
     * @param node the node to rotate around
     */
    protected void rotateRight(RBNode<K, V> node) {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("RedBlackTree.rotateRight() not yet implemented");
    }

    /**
     * Fixes Red-Black property violations after insertion.
     *
     * @param node the newly inserted node
     */
    protected void fixup(RBNode<K, V> node) {
        // TODO: Feature 3 team — implement this (handle uncle-red and uncle-black cases)
        throw new UnsupportedOperationException("RedBlackTree.fixup() not yet implemented");
    }

    /** Returns the number of nodes. */
    public int size() { return size; }

    /** Returns true if the tree is empty. */
    public boolean isEmpty() { return root == null; }

    /** Returns the black-height of the tree (for balance verification). */
    public int height() {
        // TODO: Feature 3 team — implement this
        throw new UnsupportedOperationException("RedBlackTree.height() not yet implemented");
    }
}
